#include <config.h>

#if defined(_WIN_)
    #include <windows.h>
    #include "util/str.h"
#endif

#include "gs_mission_manager.h"
#include "gs_paths.h"
#include "gs_cmd.h"
#include "gs_input_handlers.h"
#include "event_parser.h"
#include "util/common.h"
#include "util/print_status.h"
#include "util/l10n.h"
#include "util/file.h"
#include "util/stack/circular_stack.h"
#include "mxml/mxml.h"

#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <pthread.h>

#define XML_ROOT            ("missions")
#define XML_ELEM            ("mission")

#define XML_ATTR_NAME       ("name")
#define XML_ATTR_PATH       ("path")
#define XML_ATTR_DURATION   ("duration")
#define XML_ATTR_NEXT       ("next")
#define XML_ATTR_NEXT_RED   ("nextRed")
#define XML_ATTR_NEXT_BLUE  ("nextBlue")
#define XML_ATTR_IS_CURRENT ("isCurrent")
#define IS_CURRENT_TRUE_VAL ("1")

#define SECONDS_LEFT_BEFORE_END (10)

static void mssn_set_current(D_MISSION_ELEM* value);
static void mssn_list_load();

static void mssn_init(D_MISSION_ELEM** _this);
static void mssn_free(D_MISSION_ELEM** _this);
static void mssn_load_weather_report(D_MISSION* mission);
static D_MISSION_ELEM* get_mssn_elem_by_name(char* name);

static void mssn_list_resolve_conflicts();
static void mssn_list_resolve_branch(char* name, void** nextRed, void** nextBlue, void** next);
static void mssn_list_save();
static void mssn_list_clear();
static void mssn_list_history_clear();
static void mssn_list_print();

static void msg_delete(MSG_T* msg);
static void msg_enqueue(MSG_T* msg);

static void* mssn_timer_watcher();
static void* mssn_msg_dispatcher();
static BOOL check_notificator_seconds(int* value, int range, int newValue);

static void* handle_events_in();


static CSTACK HISTORY;
static D_MISSION_ELEM* FIRST = NULL;
static D_MISSION_ELEM* LAST = NULL;
static D_MISSION_ELEM* CURRENT = NULL;

static int MSSN_COUNT = 0;
static int SECS_LEFT = 0;
static BOOL SECS_LEFT_CHANGED = FALSE;
static BOOL RUNNING = FALSE;
static BOOL LOADED = FALSE;
static BOOL INTERRUPTED = FALSE;
static BOOL DO_WORK = FALSE;

static MSG_QUEUE MSG_Q;
static pthread_cond_t  MSG_CND = PTHREAD_COND_INITIALIZER;
static pthread_mutex_t MSG_MTX = PTHREAD_MUTEX_INITIALIZER;

static pthread_t H_TIMER;
static pthread_t H_EVENT_PARSER;
static pthread_t H_MSG_DISPATCHER;

void mssn_list_load()
{
    PRINT_STATUS_NEW(tr("Loading missions list"));

    if (gs_check_path_mission_list() == FALSE)
    {
        PRINT_STATUS_FAIL();
        return;
    }

    FILE* fp;
    mxml_node_t* tree;

    fp = fopen(PATH_GS_MISSION_LIST, "r");
    tree = mxmlLoadFile(NULL, fp, MXML_TEXT_CALLBACK);
    fclose(fp);

    if (tree == NULL)
    {
        PRINT_STATUS_MSG_ERR(tr("XML document is invalid"));
        PRINT_STATUS_FAIL();
        return;
    }

    const char *attrVal;

    mxml_node_t* node;
    mxml_node_t* nodeNextRed;
    mxml_node_t* nodeNextBlue;
    mxml_node_t* nodeNext;

    D_MISSION_ELEM* elem = NULL;
    char msg[100];

    int i = 0;

    for (node = mxmlFindElement(tree, tree, NULL, NULL, NULL, MXML_DESCEND);
        node != NULL;
        node=mxmlWalkNext (node, NULL, MXML_DESCEND))
    {
        if (node->type != MXML_ELEMENT) continue;
        if (strcmp(mxmlGetElement(node), XML_ELEM) == 0)
        {
            i++;

            elem = NULL;
            mssn_init(&elem);

            // Set name
            attrVal = mxmlElementGetAttr(node, XML_ATTR_NAME);
            if (attrVal == NULL)
            {
                sprintf(msg, "#%d : %s ", i, tr("name is not set. Skipping"));
                PRINT_STATUS_MSG_ERR(msg);

                mssn_free(&elem);
                continue;
            } else if (get_mssn_elem_by_name((char*)attrVal) != NULL)
            {
                sprintf(msg, "\"%s\" : %s", attrVal, tr("Name already exists in list. Skipping"));
                PRINT_STATUS_MSG_ERR(msg);

                mssn_free(&elem);
                continue;
            }

#ifdef _WIN_
            char reEncoded[255];
            utf8_to_cp1251((char*)attrVal, (char*)reEncoded);
            d_mission_name_set(elem->data, (char*)reEncoded);
#else
            d_mission_name_set(elem->data, (char*)attrVal);
#endif

            // Set path
            attrVal = mxmlElementGetAttr(node, XML_ATTR_PATH);
            if (attrVal == NULL)
            {
                sprintf(msg, "\"%s\" : %s", elem->data->name, tr("path is not set. Skipping"));
                PRINT_STATUS_MSG_ERR(msg);

                mssn_free(&elem);
                continue;
            } else if (gs_check_path_mission(elem->data->name, (char*)attrVal) == FALSE)
            {
                mssn_free(&elem);
                continue;
            }
            d_mission_path_set(elem->data, (char*)attrVal);

            // Set duration
            elem->data->sDuration = 0;
            attrVal = mxmlElementGetAttr(node, XML_ATTR_DURATION);

            if (attrVal != NULL)
                elem->data->sDuration = atoi(attrVal);

            if (elem->data->sDuration == 0)
            {
                sprintf(msg, "\"%s\" : %s", elem->data->name, tr("duration is not set. Using default value"));
                PRINT_STATUS_MSG_WRN(msg);

                elem->data->sDuration = MSSN_DEFAULT_DURATION;
            }

            // Find next red
            nodeNextRed = NULL;
            attrVal = mxmlElementGetAttr(node, XML_ATTR_NEXT_RED);
            if (attrVal != NULL)
                nodeNextRed = mxmlFindElement(tree, tree, XML_ELEM,
                                           XML_ATTR_NAME,
                                           attrVal,
                                           MXML_DESCEND);

            // Find next blue
            nodeNextBlue = NULL;
            attrVal = mxmlElementGetAttr(node, XML_ATTR_NEXT_BLUE);
            if (attrVal != NULL)
                nodeNextBlue = mxmlFindElement(tree, tree, XML_ELEM,
                                           XML_ATTR_NAME,
                                           attrVal,
                                           MXML_DESCEND);

            // Find next
            nodeNext = NULL;
            attrVal = mxmlElementGetAttr(node, XML_ATTR_NEXT);
            if (attrVal != NULL)
                nodeNext = mxmlFindElement(tree, tree, XML_ELEM,
                                        XML_ATTR_NAME,
                                        attrVal,
                                        MXML_DESCEND);

            mssn_list_resolve_branch(elem->data->name,
                                     ((void**)&nodeNextRed),
                                     ((void**)&nodeNextBlue),
                                     ((void**)&nodeNext));

            // Load weather report
            mssn_load_weather_report(elem->data);

            // Check current
            attrVal = mxmlElementGetAttr(node, XML_ATTR_IS_CURRENT);
            if ((attrVal != NULL) && (strcmp(attrVal, IS_CURRENT_TRUE_VAL) == 0))
            {
                if (CURRENT != NULL)
                {
                    sprintf(msg, "%s \"%s\" -> \"%s\"",
                            tr("Only one mission can be current. Changing"),
                            CURRENT->data->name,
                            elem->data->name);
                    PRINT_STATUS_MSG_WRN(msg);
                }
                CURRENT = elem;
            }

            // Add to list
            if (FIRST == NULL)
            {
                FIRST = elem;
                LAST = FIRST;
            } else {
                LAST->next = elem;
                LAST = elem;
            }

            MSSN_COUNT++;

            elem->mNext     = (nodeNext     == NULL) ? NULL : (D_MISSION_ELEM*) nodeNext;
            elem->mNextRed  = (nodeNextRed  == NULL) ? NULL : (D_MISSION_ELEM*) nodeNextRed;
            elem->mNextBlue = (nodeNextBlue == NULL) ? NULL : (D_MISSION_ELEM*) nodeNextBlue;
        }
    }

    if (CURRENT == NULL)
        mssn_set_current(FIRST);

    mssn_list_resolve_conflicts();

    if (CURRENT == NULL)
        mssn_set_current(FIRST);

    mxmlDelete(tree);

    if (MSSN_COUNT == 0)
    {
        PRINT_STATUS_MSG_WRN(tr("Missions list is empty"));
        return;
    } else mssn_list_print();

    PRINT_STATUS_DONE();
}

void mssn_list_print()
{
    PRINT_STATUS_MSG(tr("Missions:"));

    char msg[255];

    char* redName;
    char* blueName;
    char* nextName;
    char* noneName = "none";

    D_MISSION_ELEM* curr;
    int i = 1;
    for (curr = FIRST;
         curr != NULL;
         i++, curr = curr->next)
    {
        char ch = (curr == CURRENT)?'*':' ';

        redName  = (curr->mNextRed  != NULL) ? curr->mNextRed->data->name   : noneName;
        blueName = (curr->mNextBlue != NULL) ? curr->mNextBlue->data->name  : noneName;
        nextName = (curr->mNext     != NULL) ? curr->mNext->data->name      : noneName;

        sprintf(msg, "%2d %c %-10s -> (r: %-10s | b: %-10s | n: %-10s)",
                i,
                ch,
                curr->data->name,
                redName,
                blueName,
                nextName);
        PRINT_STATUS_MSG(msg);
    }
}

void mssn_init(D_MISSION_ELEM** _this)
{
    if ((*_this) == NULL)
        (*_this) = (struct D_MISSION_ELEM*) malloc(sizeof (struct D_MISSION_ELEM));

    (*_this)->data      = NULL;
    (*_this)->next      = NULL;
    (*_this)->mNext     = NULL;
    (*_this)->mNextRed  = NULL;
    (*_this)->mNextBlue = NULL;
    (*_this)->refsCount = 0;

    d_mission_init(&((*_this)->data));
}

void mssn_free(D_MISSION_ELEM** _this)
{
    if ((*_this) == NULL) return;

    d_mission_free(&((*_this)->data));
    free(*_this);
    *_this = NULL;
}

D_MISSION_ELEM* get_mssn_elem_by_name(char* name)
{
    D_MISSION_ELEM* result = NULL;
    D_MISSION_ELEM* elem = FIRST;

    while (elem != NULL)
    {
        if (strcmp(elem->data-> name, name) == 0)
        {
            result = elem;
            break;
        }
        elem = elem->next;
    }

    return result;
}

void mssn_load_weather_report(D_MISSION* mission)
{
    unsigned int paramsLeft = (1 << 10)-1;

    int line_len = 64;
    char line[line_len];
    int offset = 0;
    RL_STAT stat;

    int o_flags = O_RDONLY;

#if !defined(_WIN_)
    o_flags |= O_NONBLOCK;
#endif

    char missionPath[255];
    sprintf(missionPath, "%s%s", PATH_GS_MISSIONS_DIR, mission->path);

    int stream = open(missionPath, o_flags);
    while(paramsLeft > 0)
    {
        line_rd(stream, line, line_len, offset, &stat);
        if (stat.finished == FALSE)
        {
            if (stat.length == 0)
                break;
            offset += stat.length;
        } else {
            offset = 0;
            if ((strstr(line, MSSN_KEY_TIME) != NULL)                   && (paramsLeft & 0x01))
            {
#ifdef _WIN_
                uint2 h, m;
                sscanf(line, "%*s %hu.%hu", &h, &m);

                mission->weather->publGameTS.hour   = (uint1)(h & 0xFF);
                mission->weather->publGameTS.minute = (uint1)(m & 0xFF);
#else
                sscanf(line, "%*s %hhu.%hhu",
                             &(mission->weather->publGameTS.hour),
                             &(mission->weather->publGameTS.minute));
#endif
                paramsLeft &= ~(0x01);
            } else if ((strstr(line, MSSN_KEY_CLOUD_TYPE) != NULL)      && (paramsLeft & (0x01 << 1)))
            {
#ifdef _WIN_
                uint2 w;
                sscanf(line, "%*s %hu", &w);
                mission->weather->weather = (D_WEATHER)(w & 0xFF);
#else
                sscanf(line, "%*s %hhu", (uint1*)&(mission->weather->weather));
#endif
                paramsLeft &= ~(0x01 << 1);
            } else if ((strstr(line, MSSN_KEY_CLOUD_HEIGTH) != NULL)    && (paramsLeft & (0x01 << 2)))
            {
                sscanf(line, "%*s %hu", &(mission->weather->cloudsHeightM));
                paramsLeft &= ~(0x01 << 2);
            } else if ((strstr(line, MSSN_KEY_YEAR) != NULL)            && (paramsLeft & (0x01 << 3)))
            {
                sscanf(line, "%*s %hu", &(mission->weather->publGameTS.year));
                paramsLeft &= ~(0x01 << 3);
            } else if ((strstr(line, MSSN_KEY_MONTH) != NULL)           && (paramsLeft & (0x01 << 4)))
            {
#ifdef _WIN_
                uint2 m;
                sscanf(line, "%*s %hu", &m);
                mission->weather->publGameTS.month = (uint1)(m & 0xFF);
#else
                sscanf(line, "%*s %hhu", &(mission->weather->publGameTS.month));
#endif
                paramsLeft &= ~(0x01 << 4);
            } else if ((strstr(line, MSSN_KEY_DAY) != NULL)             && (paramsLeft & (0x01 << 5)))
            {
#ifdef _WIN_
                uint2 d;
                sscanf(line, "%*s %hu", &d);
                mission->weather->publGameTS.day = (uint1)(d & 0xFF);
#else
                sscanf(line, "%*s %hhu", &(mission->weather->publGameTS.day));
#endif
                paramsLeft &= ~(0x01 << 5);
            } else if ((strstr(line, MSSN_KEY_WIND_DIRECTION) != NULL)  && (paramsLeft & (0x01 << 6)))
            {
                sscanf(line, "%*s %hu", &(mission->weather->windDirectionDeg));
                paramsLeft &= ~(0x01 << 6);
            } else if ((strstr(line, MSSN_KEY_WIND_SPEED) != NULL)      && (paramsLeft & (0x01 << 7)))
            {
                sscanf(line, "%*s %hu", &(mission->weather->windSpeedMS));
                paramsLeft &= ~(0x01 << 7);
            } else if ((strstr(line, MSSN_KEY_GUST) != NULL)            && (paramsLeft & (0x01 << 8)))
            {
#ifdef _WIN_
                uint2 g;
                sscanf(line, "%*s %hu", &g);
                mission->weather->gust = (D_GUST)(g & 0xFF);
#else
                sscanf(line, "%*s %hhu", (uint1*)&(mission->weather->gust));
#endif
                paramsLeft &= ~(0x01 << 8);
            } else if ((strstr(line, MSSN_KEY_TURBULENCE) != NULL)      && (paramsLeft & (0x01 << 9)))
            {
#ifdef _WIN_
                uint2 t;
                sscanf(line, "%*s %hu", &t);
                mission->weather->turbulence = (D_TURBULENCE)(t & 0xFF);
#else
                sscanf(line, "%*s %hhu", (uint1*)&(mission->weather->turbulence));
#endif
                paramsLeft &= ~(0x01 << 9);
            }
        }
    }
    close(stream);
}

void mssn_list_resolve_conflicts()
{
    if (FIRST == NULL) return;

    D_MISSION_ELEM* curr = FIRST;

    D_MISSION_ELEM* red;
    D_MISSION_ELEM* blue;
    D_MISSION_ELEM* none;

    mxml_node_t* node;
    const char *name;

    while (curr != NULL)
    {
        node = (mxml_node_t*)(curr->mNextRed);
        name = mxmlElementGetAttr(node, XML_ATTR_NAME);
        red = NULL;
        if (name != NULL)
            red = get_mssn_elem_by_name((char*)name);

        node = (mxml_node_t*)(curr->mNextBlue);
        name = mxmlElementGetAttr(node, XML_ATTR_NAME);
        blue = NULL;
        if (name != NULL)
            blue = get_mssn_elem_by_name((char*)name);

        node = (mxml_node_t*)(curr->mNext);
        name = mxmlElementGetAttr(node, XML_ATTR_NAME);
        none = NULL;
        if (name != NULL)
            none = get_mssn_elem_by_name((char*)name);

        mssn_list_resolve_branch(curr->data->name,
                                 ((void**)&red),
                                 ((void**)&blue),
                                 ((void**)&none));

        if (red != NULL)
            red->refsCount++;
        if ((blue != NULL) && (blue != red))
            blue->refsCount++;
        if ((none != NULL) && (none != red) && (none != blue))
            none->refsCount++;

        curr->mNext = none;
        curr->mNextRed = red;
        curr->mNextBlue = blue;

        curr = curr->next;
    }

    if (MSSN_COUNT==1) return;

    char msg[100];
    D_MISSION_ELEM* prev = NULL;

    BOOL unreferencedFound;

    while (1)
    {
        unreferencedFound = FALSE;

        for (curr = FIRST;
             curr != NULL;
             prev = curr, curr = curr->next)
        {

            if ((curr->refsCount == 0) && (curr != CURRENT))
            {
                unreferencedFound = TRUE;

                sprintf(msg, "\"%s\" %s",
                        curr->data->name,
                        tr("has no references. Deleting"));
                PRINT_STATUS_MSG_ERR(msg);
                MSSN_COUNT--;

                if (curr->mNextRed != NULL)
                    curr->mNextRed->refsCount--;
                if ((curr->mNextBlue != NULL) && (curr->mNextBlue != curr->mNextRed))
                    curr->mNextBlue->refsCount--;
                if ((curr->mNext != NULL) && (curr->mNext != curr->mNextRed) && (curr->mNext != curr->mNextBlue))
                    curr->mNext->refsCount--;

                if (prev == NULL) {
                    FIRST = curr->next;

                    if (curr == LAST)
                        LAST = FIRST;
                } else {
                    prev->next = curr->next;
                    if (curr == LAST)
                        LAST = prev;
                }

                if (curr == CURRENT) CURRENT = FIRST;

                mssn_free(&curr);
                curr = prev;

                continue;
            }
        }

        if (unreferencedFound == FALSE) break;
    }

    for (curr = FIRST;
         curr != NULL;
         prev = curr, curr = curr->next)
    {
        if ((curr->mNextRed == NULL)
        &&  (curr->mNextBlue == NULL)
        &&  (curr->mNext == NULL))
        {
            sprintf(msg, "\"%s\" %s",
                    curr->data->name,
                    tr("has no next missions. This may cause playing mission list only once"));
            PRINT_STATUS_MSG_WRN(msg);
        }
    }
}

void mssn_list_resolve_branch(char* name, void** nextRed, void** nextBlue, void** next)
{
    char msg[100];

    if ((*next) != NULL)
    {
        if (((*nextRed) != NULL) && ((*nextBlue) == NULL))
        {
            sprintf(msg, "\"%s\" : %s",
                    name,
                    tr("setting next BLUE map equal to next NONE map"));
            PRINT_STATUS_MSG_WRN(msg);

            (*nextBlue) = (*next);
        } else if (((*nextRed) == NULL) && ((*nextBlue) != NULL))
        {
            sprintf(msg, "\"%s\" : %s",
                    name,
                    tr("setting next RED map equal to next NONE map"));
            PRINT_STATUS_MSG_WRN(msg);

            (*nextRed) = (*next);
        } else if (((*nextRed) == NULL) && ((*nextBlue) == NULL))
        {
            sprintf(msg, "\"%s\" : %s",
                    name,
                    tr("setting next RED and BLUE maps equal to next NONE map"));
            PRINT_STATUS_MSG_WRN(msg);

            (*nextRed)  = (*next);
            (*nextBlue) = (*next);
        }
    } else {
        if (((*nextRed) != NULL) && ((*nextBlue) == NULL))
        {
            sprintf(msg, "\"%s\" : %s",
                    name,
                    tr("setting next BLUE and NONE maps equal to next RED map"));
            PRINT_STATUS_MSG_WRN(msg);

            (*next) = (*nextRed);
            (*nextBlue) = next;
        } else if (((*nextRed) == NULL) && ((*nextBlue) != NULL))
        {
            sprintf(msg, "\"%s\" : %s",
                    name,
                    tr("setting next RED and NONE maps equal to next BLUE map"));
            PRINT_STATUS_MSG_WRN(msg);

            (*next) = (*nextBlue);
            (*nextRed) = (*next);
        } else if (((*nextRed) != NULL) && ((*nextBlue) != NULL))
        {
            sprintf(msg, "\"%s\" : %s",
                    name,
                    tr("setting next NONE map equal to next RED map"));
            PRINT_STATUS_MSG_WRN(msg);

            (*next)  = (*nextRed);
        }
    }
}

void mssn_list_save()
{
    PRINT_STATUS_NEW(tr("Saving missions list"));

    mxmlSetWrapMargin(0);

    mxml_node_t* tree;
    mxml_node_t* root;
    mxml_node_t* node;

    tree = mxmlNewXML("1.0");
    root = mxmlNewElement(tree, XML_ROOT);

    D_MISSION_ELEM* elem;

    for (elem = FIRST; elem != NULL; elem = elem->next)
    {
        node = mxmlNewElement(root, XML_ELEM);

        mxmlElementSetAttr(node, XML_ATTR_NAME, elem->data->name);
        mxmlElementSetAttr(node, XML_ATTR_PATH, elem->data->path);
        mxmlElementSetAttrf(node, XML_ATTR_DURATION, "%d", elem->data->sDuration);

        if (elem == CURRENT)
            mxmlElementSetAttr(node, XML_ATTR_IS_CURRENT, IS_CURRENT_TRUE_VAL);

        if (elem->mNext != NULL)
            mxmlElementSetAttr(node, XML_ATTR_NEXT, elem->mNext->data->name);
        if (elem->mNextRed != NULL)
            mxmlElementSetAttr(node, XML_ATTR_NEXT_RED, elem->mNextRed->data->name);
        if (elem->mNextBlue != NULL)
            mxmlElementSetAttr(node, XML_ATTR_NEXT_BLUE, elem->mNextBlue->data->name);
    }

    FILE *fp;
    fp = fopen(PATH_GS_MISSION_LIST, "w");

    mxmlSaveFile(tree, fp, MXML_NO_CALLBACK);
    fclose(fp);

    PRINT_STATUS_DONE();
}

void mssn_list_clear()
{
    if (FIRST == NULL) return;

    PRINT_STATUS_NEW(tr("Clearing missions list"));

    D_MISSION_ELEM* curr = FIRST;
    D_MISSION_ELEM* prev = NULL;

    while (curr != NULL)
    {
        prev = curr;
        curr = curr->next;

        mssn_free(&prev);
    }

    FIRST = NULL;
    LAST = NULL;
    CURRENT = NULL;

    PRINT_STATUS_DONE();
}

void mssn_set_current(D_MISSION_ELEM* value)
{
    CURRENT = value;
    mssn_list_save();
}

void gs_mssn_load()
{
    if (LOADED == TRUE)
    {
        PRINT_STATUS_MSG_WRN(tr("Mission is already loaded"));
        return;
    }

    char* msg1 = tr("Loading mission");
    char* msgFailed = tr("Mission loading failed.");
    char* msgLoaded = tr("Mission loaded.");
    char msg2[100];

    if (CURRENT != NULL)
    {
        sprintf(msg2, "%s '%s'...", msg1, CURRENT->data->name);

        PRINT_STATUS_NEW(msg2);
        gs_cmd_chat_all(msg2);
    } else {
        sprintf(msg2, "%s...", msg1);
        PRINT_STATUS_NEW(msg2);
        PRINT_STATUS_MSG_ERR(tr("Current mission is not selected"));
        PRINT_STATUS_FAIL();

        gs_cmd_chat_all(msg2);
        gs_cmd_chat_all(msgFailed);
        return;
    }

    LOADED = FALSE;
    gs_cmd_mssn_load(CURRENT->data->path);

    int tries = 45;
    while ((LOADED == FALSE) && (DO_WORK == TRUE))
    {
        #if defined(_WIN_)
        Sleep(1000);
        #else
        sleep(1);
        #endif

        tries--;
        if (tries == 0)
        {
            PRINT_STATUS_MSG_ERR(tr("Mission loading timeout"));
            break;
        }
    }

    if (LOADED == TRUE)
    {
        PRINT_STATUS_DONE();
        gs_cmd_chat_all(msgLoaded);
    } else {
        PRINT_STATUS_FAIL();
        gs_cmd_chat_all(msgFailed);
    }
}

void gs_mssn_unload()
{
    if (LOADED == FALSE)
    {
        PRINT_STATUS_MSG_WRN(tr("Mission is not loaded"));
        return;
    }

    if (RUNNING == TRUE)
    {
        PRINT_STATUS_MSG_WRN(tr("Mission is still running"));
        return;
    }

    char* msg1 = tr("Unloading mission...");

    PRINT_STATUS_NEW(msg1);
    gs_cmd_chat_all(msg1);

    gs_cmd_mssn_unload();
    gs_cmd_mssn_status();

    int tries = 25;
    while ((LOADED == TRUE) && (DO_WORK == TRUE))
    {
        #if defined(_WIN_)
        Sleep(1000);
        #else
        sleep(1);
        #endif

        tries--;
        if (tries == 0)
        {
            PRINT_STATUS_MSG_ERR(tr("Mission unloading timeout"));
            break;
        }
    }

    if ((DO_WORK == FALSE)
    ||  ((LOADED == FALSE) && (DO_WORK == TRUE)))
    {
        LOADED = FALSE;
        PRINT_STATUS_DONE();
        gs_cmd_chat_all(tr("Mission unloaded."));
    } else {
        PRINT_STATUS_FAIL();
        gs_cmd_chat_all(tr("Mission unloading failed."));
    }
}

void gs_mssn_run()
{
    if (RUNNING == TRUE)
    {
        PRINT_STATUS_MSG_WRN(tr("Mission is already running"));
        return;
    }

    char* msg1 = tr("Launching mission");
    char* msgFailed = tr("Mission launching failed.");
    char* msgLaunched = tr("Mission launched.");
    char msg2[100];

    if (LOADED != FALSE)
    {
        sprintf(msg2, "%s '%s'...", msg1, CURRENT->data->name);

        PRINT_STATUS_NEW(msg2);
        gs_cmd_chat_all(msg2);
    } else {
        sprintf(msg2, "%s...", msg1);
        PRINT_STATUS_NEW(msg2);
        PRINT_STATUS_MSG_ERR(tr("Mission is not loaded"));
        PRINT_STATUS_FAIL();

        gs_cmd_chat_all(msg2);
        gs_cmd_chat_all(msgFailed);
        return;
    }

    RUNNING = FALSE;
    gs_cmd_mssn_run();

    int tries = 15;
    while ((RUNNING == FALSE) && (DO_WORK == TRUE))
    {
        #if defined(_WIN_)
        Sleep(1000);
        #else
        sleep(1);
        #endif

        tries--;
        if (tries == 0)
        {
            PRINT_STATUS_MSG_ERR(tr("Mission launching timeout"));
            break;
        }
    }

    if (RUNNING == TRUE)
    {
        PRINT_STATUS_DONE();
        gs_cmd_chat_all(msgLaunched);

        SECS_LEFT = CURRENT->data->sDuration;
        INTERRUPTED = FALSE;

        pthread_create(&H_TIMER, NULL, &mssn_timer_watcher, NULL);
        pthread_create(&H_EVENT_PARSER, NULL, &handle_events_in, NULL);

    } else {
        PRINT_STATUS_FAIL();
        gs_cmd_chat_all(msgFailed);
    }
}

void gs_mssn_end()
{
    if (RUNNING == FALSE)
    {
        PRINT_STATUS_MSG_WRN(tr("Mission is not running"));
        return;
    }

    char* msg1 = tr("Ending mission...");

    PRINT_STATUS_NEW(msg1);
    gs_cmd_chat_all(msg1);

    void *res;
    if (SECS_LEFT > SECONDS_LEFT_BEFORE_END)
    {
        INTERRUPTED = TRUE;
        gs_mssn_seconds_left_set(SECONDS_LEFT_BEFORE_END);
    }
    pthread_join(H_TIMER, &res);

    gs_cmd_mssn_end();
    gs_cmd_mssn_status();

    int tries = 25;
    while ((RUNNING == TRUE) && (DO_WORK == TRUE))
    {
        #if defined(_WIN_)
        Sleep(1000);
        #else
        sleep(1);
        #endif

        tries--;
        if (tries == 0)
        {
            PRINT_STATUS_MSG_ERR(tr("Mission ending timeout"));
            break;
        }
    }

    if ((DO_WORK == FALSE)
    ||  ((RUNNING == FALSE) && (DO_WORK == TRUE)))
    {
        RUNNING = FALSE;
        PRINT_STATUS_DONE();

        pthread_join(H_EVENT_PARSER, &res);

    } else {
        PRINT_STATUS_FAIL();
        gs_cmd_chat_all(tr("Mission ending failed."));
    }
}

void gs_mssn_rerun()
{
    PRINT_STATUS_NEW(tr("Re-running mission"));

    gs_mssn_end();
    gs_mssn_run();

    if (RUNNING == TRUE)
    {
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_FAIL();
    }
}

void gs_mssn_next()
{
    PRINT_STATUS_NEW(tr("Going to next mission"));

    if (CURRENT == NULL)
    {
        PRINT_STATUS_MSG_ERR(tr("Current mission is not selected"));
        PRINT_STATUS_FAIL();
        return;
    }

    cstack_push(&HISTORY, (void*) CURRENT);

    if (RUNNING == TRUE)
    {
        gs_mssn_stop();
        CURRENT = CURRENT->mNext;
        gs_mssn_start();

        if (RUNNING == FALSE)
        {
            PRINT_STATUS_FAIL();
            return;
        }
    } else if (LOADED == TRUE) {
        gs_mssn_unload();
        CURRENT = CURRENT->mNext;
        gs_mssn_load();

        if (LOADED == FALSE)
        {
            PRINT_STATUS_FAIL();
            return;
        }
    } else {
        CURRENT = CURRENT->mNext;
    }
    mssn_list_save();
    PRINT_STATUS_DONE();
}

void gs_mssn_prev()
{
    PRINT_STATUS_NEW(tr("Going to previous mission"));

    D_MISSION_ELEM* PREV = (D_MISSION_ELEM*) cstack_retrieve(&HISTORY);

    if (PREV == NULL)
    {
        PRINT_STATUS_MSG_ERR(tr("Previous mission is not selected"));
        PRINT_STATUS_FAIL();
        return;
    }

    if (RUNNING == TRUE)
    {
        gs_mssn_stop();
        CURRENT = PREV;
        gs_mssn_start();

        if (RUNNING == FALSE)
        {
            PRINT_STATUS_FAIL();
            return;
        }
    } else if (LOADED == TRUE) {
        gs_mssn_unload();
        CURRENT = PREV;
        gs_mssn_load();

        if (LOADED == FALSE)
        {
            PRINT_STATUS_FAIL();
            return;
        }
    } else {
        CURRENT = PREV;
    }
    mssn_list_save();
    PRINT_STATUS_DONE();
}

void gs_mssn_start()
{
    if (RUNNING == TRUE)
    {
        PRINT_STATUS_MSG_WRN(tr("Mission is already running"));
        return;
    }

    PRINT_STATUS_NEW(tr("Starting mission"));

    gs_mssn_load();

    if (LOADED == TRUE)
    {
        gs_mssn_run();
    } else {
        PRINT_STATUS_FAIL();
        return;
    }

    if (RUNNING == TRUE)
    {
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_FAIL();
    }
}

void gs_mssn_stop()
{
    PRINT_STATUS_NEW(tr("Stopping mission"));

    gs_mssn_end();

    if (RUNNING == TRUE)
    {
        PRINT_STATUS_FAIL();
        return;
    }

    gs_mssn_unload();

    if (LOADED == FALSE)
    {
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_FAIL();
    }
}

void gs_mssn_restart()
{
    PRINT_STATUS_NEW(tr("Re-starting mission"));

    gs_mssn_stop();
    gs_mssn_start();

    if (RUNNING == TRUE)
    {
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_FAIL();
    }
}

void gs_mssn_manager_notify_loaded()
{
    LOADED = TRUE;
    RUNNING = FALSE;
}

void gs_mssn_manager_notify_not_loaded()
{
    LOADED = FALSE;
    RUNNING = FALSE;
}

void gs_mssn_manager_notify_running()
{
    LOADED = TRUE;
    RUNNING = TRUE;
}

void gs_mssn_manager_init()
{
    if (DO_WORK == TRUE) return;

    PRINT_STATUS_NEW(tr("Initializing mission manager"));

    DO_WORK = TRUE;

    cstack_init(&HISTORY, HISTORY_SIZE, sizeof(D_MISSION_ELEM));

    mssn_list_load();
    pthread_create(&H_MSG_DISPATCHER, NULL, &mssn_msg_dispatcher, NULL);

    PRINT_STATUS_DONE();
}

void gs_mssn_manager_tearDown()
{
    if (DO_WORK == FALSE) return;

    PRINT_STATUS_NEW(tr("Releasing mission manager"));

    DO_WORK = FALSE;
    pthread_cond_signal(&MSG_CND);
    gs_mssn_stop();
    mssn_list_clear();
    mssn_list_history_clear();

    PRINT_STATUS_DONE();
}

void mssn_list_history_clear()
{
    D_MISSION_ELEM* item;

    while(1)
    {
        item = cstack_retrieve(&HISTORY);
        if (item == NULL) break;
    }
}

void* mssn_timer_watcher()
{
    SECS_LEFT_CHANGED = FALSE;
    int i = 0;

    int _05sec = 5;
    int _10sec = _05sec*2;
    int _15sec = _05sec*3;
    int _01min = _15sec*4;
    int _05min = _01min*5;
    int _15min = _05min*3;

    char timeStr[80];

    while (SECS_LEFT > 0)
    {
        if (SECS_LEFT_CHANGED == TRUE)
        {
            i = 0;
            SECS_LEFT_CHANGED = FALSE;
        }

        if (i == 0)
        {
            gs_mssn_time_str((char*)&timeStr);
            gs_cmd_chat_all((char*)&timeStr);
            PRINT_STATUS_MSG_NOIND((char*)&timeStr);

            if (                check_notificator_seconds(&i, _15min, _15min) == FALSE)
                if (            check_notificator_seconds(&i, _05min, _05min) == FALSE)
                    if (        check_notificator_seconds(&i, _01min, _01min) == FALSE)
                        if (    check_notificator_seconds(&i, _15sec, _15sec) == FALSE)
                            if (check_notificator_seconds(&i, _10sec, _05sec) == FALSE)
                                i = 1;
        }

        #if defined(_WIN_)
        Sleep(1000);
        #else
        sleep(1);
        #endif

        i--;
        SECS_LEFT--;
    }

    gs_cmd_chat_all(tr("Mission ended."));

    if (INTERRUPTED == TRUE)
    {
        char* msg = tr("Mission was interrupted.");
        gs_cmd_chat_all(msg);
        PRINT_STATUS_MSG_NOIND(msg);
    } else if (DO_WORK == TRUE)
        gs_mssn_next_req();

    return NULL;
}

BOOL check_notificator_seconds(int* value, int range, int newValue)
{
    if (SECS_LEFT <= range) return FALSE;

    (*value) = SECS_LEFT % newValue;
    if ((*value)==0) (*value) = newValue;

    return TRUE;
}

void gs_mssn_time_str(char* str)
{
    char* msg1 = tr("Mission time left");
    char  msg2[20];
    secondsToTimeString(SECS_LEFT, (char*)&msg2);
    sprintf(str, "%s: %s.", msg1, msg2);
}

int gs_mssn_seconds_left()
{
    return (RUNNING == TRUE) ? SECS_LEFT : 0;
}

void gs_mssn_seconds_left_set(int value)
{
    if (value < 0) return;

    if (value > SECONDS_LEFT_BEFORE_END)
    {
        SECS_LEFT = value;
    } else
    {
        SECS_LEFT = SECONDS_LEFT_BEFORE_END;
    }

    SECS_LEFT_CHANGED = TRUE;
}

void gs_mssn_time_print()
{
    char timeStr[80];
    gs_mssn_time_str((char*)&timeStr);
    PRINT_STATUS_MSG_NOIND((char*)&timeStr);
}

void gs_mssn_time_left_set(int h, int m, int s)
{
    if (RUNNING == FALSE)
    {
        PRINT_STATUS_MSG_ERR_NOIND(tr("Mission is not running"));
        return;
    }
    gs_mssn_seconds_left_set((h*3600) + (m*60) + s);
}

BOOL gs_mssn_running()
{
    return RUNNING;
}

void* mssn_msg_dispatcher()
{
    msg_queue_clear(&MSG_Q, &msg_delete);

    MSG_T* msg;

    while(DO_WORK == TRUE)
    {
        msg = msg_queue_get(&MSG_Q);
        if (msg == NULL)
        {
            pthread_mutex_lock(&MSG_MTX);
            pthread_cond_wait(&MSG_CND, &MSG_MTX);
            pthread_mutex_unlock(&MSG_MTX);
        } else {
            switch (msg->type)
            {
                case MSSN_REQ_LOAD:     gs_mssn_load();     break;
                case MSSN_REQ_UNLOAD:   gs_mssn_unload();   break;

                case MSSN_REQ_RUN:      gs_mssn_run();      break;
                case MSSN_REQ_END:      gs_mssn_end();      break;
                case MSSN_REQ_RERUN:    gs_mssn_rerun();    break;

                case MSSN_REQ_NEXT:     gs_mssn_next();     break;
                case MSSN_REQ_PREV:     gs_mssn_prev();     break;

                case MSSN_REQ_START:    gs_mssn_start();    break;
                case MSSN_REQ_STOP:     gs_mssn_stop();     break;
                case MSSN_REQ_RESTART:  gs_mssn_restart();  break;

                case MSSN_REQ_TIME_LEFT_PRINT:
                {
                    gs_mssn_time_print();
                    break;
                }
                case MSSN_REQ_TIME_LEFT_SET:
                {
                    TIMESTAMP_T* time = (TIMESTAMP_T*)(msg->data);
                    gs_mssn_time_left_set(time->hour, time->minute, time->second);
                    break;
                }

                default:
                    break;
            }
            msg_delete(msg);
        }
    }

    msg_queue_clear(&MSG_Q, &msg_delete);

    return NULL;
}

void msg_enqueue(MSG_T* msg)
{
    msg_queue_put(&MSG_Q, msg);
    pthread_cond_signal(&MSG_CND);
}

void msg_delete(MSG_T* msg)
{
    switch (msg->type)
    {
        case MSSN_REQ_TIME_LEFT_SET:
        {
            TIMESTAMP_T* time = (TIMESTAMP_T*)(msg->data);
            free(time);
            break;
        }
        default:
            break;
    }

    free(msg);
}

void gs_mssn_load_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_LOAD;
    msg_enqueue(msg);
}

void gs_mssn_unload_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_UNLOAD;
    msg_enqueue(msg);
}

void gs_mssn_run_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_RUN;
    msg_enqueue(msg);
}

void gs_mssn_end_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_END;
    msg_enqueue(msg);
}

void gs_mssn_rerun_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_RERUN;
    msg_enqueue(msg);
}

void gs_mssn_next_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_NEXT;
    msg_enqueue(msg);
}

void gs_mssn_prev_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_PREV;
    msg_enqueue(msg);
}

void gs_mssn_start_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_START;
    msg_enqueue(msg);
}

void gs_mssn_stop_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_STOP;
    msg_enqueue(msg);
}

void gs_mssn_restart_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_RESTART;
    msg_enqueue(msg);
}

void gs_mssn_time_print_req()
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = MSSN_REQ_TIME_LEFT_PRINT;
    msg_enqueue(msg);
}

void gs_mssn_time_left_set_req(int h, int m, int s)
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    TIMESTAMP_T* time = (TIMESTAMP_T*) malloc(sizeof(TIMESTAMP_T));

    time->hour = h;
    time->minute = m;
    time->second = s;

    msg->type = MSSN_REQ_TIME_LEFT_SET;
    msg->data = (void*)time;

    msg_enqueue(msg);
}

void* handle_events_in()
{
    PRINT_STATUS_MSG_NOIND(tr("Events parsing started"));

    int o_flags = O_RDONLY;

#if !defined(_WIN_)
    o_flags |= O_NONBLOCK;
#endif

    int events_fd = open(PATH_GS_LOG_EVT, o_flags);
    handle_input(events_fd, &gs_mssn_running, &line_rd, &event_parse_string);
    close(events_fd);

    PRINT_STATUS_MSG_NOIND(tr("Events parsing stopped"));
    return NULL;
}
