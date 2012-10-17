#include "gs_mission_manager.h"
#include "gs_paths.h"
#include "domain/d_mission.h"
#include "util/common.h"
#include "mxml/mxml.h"

#define XML_ROOT ("missions")

typedef struct D_MISSION_LITE_ELEM
{
    D_MISSION_LITE data;
    struct D_MISSION_LITE_ELEM* next;
} D_MISSION_LITE_ELEM;

static D_MISSION_LITE_ELEM* first = NULL;
static D_MISSION_LITE_ELEM* last = NULL;
static D_MISSION_LITE_ELEM* current = NULL;

static int secondsLeft = 0;
static BOOL running = FALSE;

void mission_status_reset()
{
    secondsLeft = 0;
    running = FALSE;
}

void mission_list_load()
{
    if (gs_check_path_missions() == FALSE) return;

    FILE* fp;
    mxml_node_t* tree;

    fp = fopen(PATH_GS_MISSIONS, "r");
    tree = mxmlLoadFile(NULL, fp, MXML_TEXT_CALLBACK);
    fclose(fp);

    mxml_node_t* root;
    root = mxmlFindElement(tree, tree, XML_ROOT,
                           NULL, NULL,
                           MXML_DESCEND);

    // TODO:

    mxmlDelete(tree);
}

void mission_list_save()
{
    // TODO:
    mxmlSetWrapMargin(0);
}

void mission_list_clear()
{
    if (first == NULL) return;

    // TODO:

    first = NULL;
    last = NULL;
    current = NULL;
}

void mission_list_reload()
{
    mission_list_clear();
    mission_status_reset();
    mission_list_load();
}

void gs_mission_load()
{
    if (current == NULL) {
        mission_list_load();
        if (first == NULL) return;
        current = first;
    }
    // TODO:
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
    gs_mission_end();
    mission_status_reset();
    gs_mission_run();
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
    gs_mission_load();
    gs_mission_run();
}

void gs_mission_stop()
{
    gs_mission_end();
    gs_mission_unload();
}

void gs_mission_restart()
{
    gs_mission_stop();
    mission_status_reset();
    gs_mission_start();
}

void gs_mission_fullstop()
{
    gs_mission_stop();
    mission_list_clear();
}

int gs_mission_seconds_left()
{
    return secondsLeft;
}
