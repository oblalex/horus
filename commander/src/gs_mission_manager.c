#include <config.h>

#if defined(_WIN_)
    #include <windows.h>
#endif

#include "gs_mission_manager.h"
#include "gs_paths.h"
#include "util/common.h"
#include "util/print_status.h"
#include "util/l10n.h"
#include "mxml/mxml.h"
#include "util/file.h"

#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

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


static D_MISSION_LITE_ELEM* first = NULL;
static D_MISSION_LITE_ELEM* last = NULL;
static D_MISSION_LITE_ELEM* current = NULL;

static int mssnCount = 0;
static int secondsLeft = 0;
static BOOL running = FALSE;
static BOOL loaded = FALSE;

void mssn_status_reset()
{
    PRINT_STATUS_NEW(tr("Resetting mission status"));

    secondsLeft = 0;
    running = FALSE;
    loaded = FALSE;

    PRINT_STATUS_DONE();
}

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

    D_MISSION_LITE_ELEM* elem;
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

            elem = (D_MISSION_LITE_ELEM*)malloc(sizeof(D_MISSION_LITE_ELEM));
            elem->next = NULL;
            elem->refsCount = 0;

            // Set name
            attrVal = mxmlElementGetAttr(node, XML_ATTR_NAME);

            if (attrVal == NULL)
            {
                sprintf(msg, "#%d : %s ", i, tr("name is not set. Skipping"));
                PRINT_STATUS_MSG_ERR(msg);

                free(elem);
                continue;
            } else if (get_mssn_elem_by_name(attrVal) != NULL)
            {
                sprintf(msg, "\"%s\" : %s", attrVal, tr("Name already exists in list. Skipping"));
                PRINT_STATUS_MSG_ERR(msg);

                free(elem);
                continue;
            }
            elem->data.name = (char*) malloc(sizeof(char)*strlen(attrVal));
            strcpy(elem->data.name, attrVal);

            // Set path
            attrVal = mxmlElementGetAttr(node, XML_ATTR_PATH);
            if (attrVal == NULL)
            {
                sprintf(msg, "\"%s\" : %s", elem->data.name, tr("path is not set. Skipping"));
                PRINT_STATUS_MSG_ERR(msg);

                free(elem);
                continue;
            } else if (gs_check_path_mission(attrVal) == FALSE)
            {
                free(elem);
                continue;
            }
            elem->data.path = (char*) malloc(sizeof(char)*strlen(attrVal));
            strcpy(elem->data.path, attrVal);

            // Set duration
            elem->data.sDuration = 0;
            attrVal = mxmlElementGetAttr(node, XML_ATTR_DURATION);

            if (attrVal != NULL)
                elem->data.sDuration = atoi (attrVal);

            if (elem->data.sDuration == 0)
            {
                sprintf(msg, "\"%s\" : %s", elem->data.name, tr("duration is not set. Using default value"));
                PRINT_STATUS_MSG_WRN(msg);

                elem->data.sDuration = DEFAULT_MISSION_DURATION;
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

            mssn_list_resolve_branch(elem->data.name,
                                     ((void**)&nodeNextRed),
                                     ((void**)&nodeNextBlue),
                                     ((void**)&nodeNext));

            // Load weather report
            mssn_load_weather_report(&(elem->data));

            // Check current
            attrVal = mxmlElementGetAttr(node, XML_ATTR_IS_CURRENT);
            if ((attrVal != NULL) && (strcmp(attrVal, IS_CURRENT_TRUE_VAL) == 0))
            {
                if (current != NULL)
                {
                    sprintf(msg, "%s \"%s\" -> \"%s\"",
                            tr("Only one mission can be current. Changing"),
                            current->data.name,
                            elem->data.name);
                    PRINT_STATUS_MSG_WRN(msg);
                }
                current = elem;
            }

            // Add to list
            if (first == NULL)
            {
                first = elem;
                last = first;
            } else {
                last->next = elem;
                last = elem;
            }

            mssnCount++;

            if ((nodeNextRed == NULL)
            &&  (nodeNextBlue == NULL)
            &&  (nodeNext == NULL))
            {
                break;
            } else {
                elem->mNext     = (D_MISSION_LITE_ELEM*) nodeNext;
                elem->mNextRed  = (D_MISSION_LITE_ELEM*) nodeNextRed;
                elem->mNextBlue = (D_MISSION_LITE_ELEM*) nodeNextBlue;
            }
        }
    }

    if (current == NULL)
        mssn_set_current(first);

    mssn_list_resolve_conflicts();

    if (current == NULL)
        mssn_set_current(first);

    mxmlDelete(tree);

    if (mssnCount == 0)
    {
        PRINT_STATUS_MSG_WRN(tr("Missions list is empty"));
        return;
    } else mssn_list_print();

    PRINT_STATUS_DONE();
}

void mssn_list_print()
{
    PRINT_STATUS_MSG(tr("Missions:"));

    char* msg[180];

    D_MISSION_LITE_ELEM* curr;
    int i = 1;
    for (curr = first;
         curr != NULL;
         i++, curr = curr->next)
    {
        char ch = (curr == current)?'*':' ';

        sprintf(msg, "%2d %c %-10s -> (r: %-10s | b: %-10s | n: %-10s)",
                i,
                ch,
                curr->data.name,
                curr->data.name,
                curr->data.name,
                curr->data.name);
        PRINT_STATUS_MSG(msg);
    }
}

D_MISSION_LITE_ELEM* get_mssn_elem_by_name(char* name)
{
    D_MISSION_LITE_ELEM* result = NULL;
    D_MISSION_LITE_ELEM* elem = first;

    while (elem != NULL)
    {
        if (strcmp(elem->data.name, name) == 0)
        {
            result = elem;
            break;
        }
        elem = elem->next;
    }

    return result;
}

void mssn_load_weather_report(D_MISSION_LITE* mission)
{
    mission->weather.publGameTS.second      = 0;
    mission->weather.publGameTS.millisecond = 0;


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
            if ((strstr(line, MSSN_KEY_TIME) != NULL)                   && (paramsLeft && 0x01))
            {
                sscanf(line, "%*s %d.%d",
                       &(mission->weather.publGameTS.hour),
                       &(mission->weather.publGameTS.minute));
                paramsLeft &= ~(0x01);
            } else if ((strstr(line, MSSN_KEY_CLOUD_TYPE) != NULL)      && (paramsLeft && (0x01 << 1)))
            {
                sscanf(line, "%*s %d", (int*)&(mission->weather.weather));
                paramsLeft &= ~(0x01 << 1);
            } else if ((strstr(line, MSSN_KEY_CLOUD_HEIGTH) != NULL)    && (paramsLeft && (0x01 << 2)))
            {
                sscanf(line, "%*s %d", &(mission->weather.cloudsHeightM));
                paramsLeft &= ~(0x01 << 2);
            } else if ((strstr(line, MSSN_KEY_YEAR) != NULL)            && (paramsLeft && (0x01 << 3)))
            {
                sscanf(line, "%*s %d", &(mission->weather.publGameTS.year));
                paramsLeft &= ~(0x01 << 3);
            } else if ((strstr(line, MSSN_KEY_MONTH) != NULL)           && (paramsLeft && (0x01 << 4)))
            {
                sscanf(line, "%*s %d", &(mission->weather.publGameTS.month));
                paramsLeft &= ~(0x01 << 4);
            } else if ((strstr(line, MSSN_KEY_DAY) != NULL)             && (paramsLeft && (0x01 << 5)))
            {
                sscanf(line, "%*s %d", &(mission->weather.publGameTS.day));
                paramsLeft &= ~(0x01 << 5);
            } else if ((strstr(line, MSSN_KEY_WIND_DIRECTION) != NULL)  && (paramsLeft && (0x01 << 6)))
            {
                sscanf(line, "%*s %d", &(mission->weather.windDirectionDeg));
                paramsLeft &= ~(0x01 << 6);
            } else if ((strstr(line, MSSN_KEY_WIND_SPEED) != NULL)      && (paramsLeft && (0x01 << 7)))
            {
                sscanf(line, "%*s %d", &(mission->weather.windSpeedMS));
                paramsLeft &= ~(0x01 << 7);
            } else if ((strstr(line, MSSN_KEY_GUST) != NULL)            && (paramsLeft && (0x01 << 8)))
            {
                sscanf(line, "%*s %d", (int*)&(mission->weather.gust));
                paramsLeft &= ~(0x01 << 8);
            } else if ((strstr(line, MSSN_KEY_TURBULENCE) != NULL)      && (paramsLeft && (0x01 << 9)))
            {
                sscanf(line, "%*s %d", (int*)&(mission->weather.turbulence));
                paramsLeft &= ~(0x01 << 9);
            }
        }
    }
    close(stream);
}

void mssn_list_resolve_conflicts()
{
    if (first == NULL) return;

    first->refsCount++;

    if (mssnCount==1) return;

    D_MISSION_LITE_ELEM* curr = first;

    D_MISSION_LITE_ELEM* red;
    D_MISSION_LITE_ELEM* blue;
    D_MISSION_LITE_ELEM* none;

    mxml_node_t* node;
    const char *name;

    while (curr != NULL)
    {
        node = (mxml_node_t*)(curr->mNextRed);
        name = mxmlElementGetAttr(node, XML_ATTR_NAME);
        red = NULL;
        if (name != NULL)
            red = get_mssn_elem_by_name(name);

        node = (mxml_node_t*)(curr->mNextBlue);
        name = mxmlElementGetAttr(node, XML_ATTR_NAME);
        blue = NULL;
        if (name != NULL)
            blue = get_mssn_elem_by_name(name);

        node = (mxml_node_t*)(curr->mNext);
        name = mxmlElementGetAttr(node, XML_ATTR_NAME);
        none = NULL;
        if (name != NULL)
            none = get_mssn_elem_by_name(name);

        mssn_list_resolve_branch(curr->data.name,
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

    char msg[100];
    D_MISSION_LITE_ELEM* prev = NULL;

    BOOL unreferencedFound;

    while (1)
    {
        unreferencedFound = FALSE;

        for (curr = first;
             curr != NULL;
             prev = curr, curr = curr->next)
        {

            if (curr->refsCount == 0)
            {
                unreferencedFound = TRUE;

                sprintf(msg, "\"%s\" %s",
                        curr->data.name,
                        tr("has no references. Deleting"));
                PRINT_STATUS_MSG_ERR(msg);
                mssnCount--;

                if (curr->mNextRed != NULL)
                    curr->mNextRed->refsCount--;
                if ((curr->mNextBlue != NULL) && (curr->mNextBlue != curr->mNextRed))
                    curr->mNextBlue->refsCount--;
                if ((curr->mNext != NULL) && (curr->mNext != curr->mNextRed) && (curr->mNext != curr->mNextBlue))
                    curr->mNext->refsCount--;

                if (prev == NULL) {
                    first = curr->next;

                    if (curr == last)
                        last = first;
                } else {
                    prev->next = curr->next;
                    if (curr == last)
                        last = prev;
                }

                if (curr == current) current = first;

                free(curr);
                curr = prev;

                continue;
            }
        }

        if (unreferencedFound == FALSE) break;
    }

    for (curr = first;
         curr != NULL;
         prev = curr, curr = curr->next)
    {
        if ((curr->mNextRed == NULL)
        &&  (curr->mNextBlue == NULL)
        &&  (curr->mNext == NULL))
        {
            sprintf(msg, "\"%s\" %s",
                    curr->data.name,
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
    // TODO:
    mxmlSetWrapMargin(0);
}

void mssn_list_clear()
{
    if (first == NULL) return;

    PRINT_STATUS_NEW(tr("Clearing missions list"));

    D_MISSION_LITE_ELEM* curr = first;
    D_MISSION_LITE_ELEM* prev = NULL;

    while (curr != NULL)
    {
        prev = curr;
        curr = curr->next;
        free(curr);
    }

    first = NULL;
    last = NULL;
    current = NULL;

    PRINT_STATUS_DONE();
}

void mssn_list_reload()
{
    PRINT_STATUS_NEW(tr("Reloading missions list"));

    mssn_list_clear();
    mssn_status_reset();
    mssn_list_load();

    PRINT_STATUS_DONE();
}

void mssn_set_current(D_MISSION_LITE_ELEM* value)
{
    current = value;
    mssn_list_save();
}

void gs_mission_load()
{
    if (loaded == TRUE)
    {
        PRINT_STATUS_MSG(tr("Mission is already loaded"));
        return;
    }

    PRINT_STATUS_NEW(tr("Loading mission"));

    if (current == NULL) {
        PRINT_STATUS_MSG_ERR(tr("Current mission is not selected"));
        PRINT_STATUS_FAIL();
        return;
    }

    // TODO:

    loaded = TRUE;
    PRINT_STATUS_DONE();
}

void gs_mission_unload()
{
    // TODO:
}

void gs_mission_run()
{
    // TODO:
}

void gs_mission_end()
{
    // TODO:
}

void gs_mission_rerun()
{
    PRINT_STATUS_NEW(tr("Re-running mission"));

    gs_mission_end();

    if (current != NULL)
    {
        mssn_status_reset();
        gs_mission_run();
    } else {
        PRINT_STATUS_FAIL();
        return;
    }

    if (running == TRUE)
    {
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_FAIL();
    }
}

void gs_mission_next()
{
    // TODO:
}

void gs_mission_prev()
{
    // TODO:
}

void gs_mission_start()
{
    PRINT_STATUS_NEW(tr("Starting mission"));

    gs_mission_load();

    if (loaded == TRUE)
    {
        gs_mission_run();
    } else {
        PRINT_STATUS_FAIL();
        return;
    }

    if (running == TRUE)
    {
        // TODO: run timer
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_FAIL();
    }
}

void gs_mission_stop()
{
    // TODO: msg
    gs_mission_end();
    gs_mission_unload();
    // TODO: msg
}

void gs_mission_restart()
{
    // TODO: msg
    gs_mission_stop();
    mssn_status_reset();
    gs_mission_start();
    // TODO: msg
}

void gs_mission_manager_init()
{
    PRINT_STATUS_NEW(tr("Initializing mission manager"));

    mssn_list_load();

    PRINT_STATUS_DONE();
}

void gs_mission_manager_tearDown()
{
    PRINT_STATUS_NEW(tr("Releasing mission manager"));

    gs_mission_stop();
    mssn_list_clear();

    PRINT_STATUS_DONE();
}

int gs_mission_seconds_left()
{
    return secondsLeft;
}
