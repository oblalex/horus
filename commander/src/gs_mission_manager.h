#ifndef GS_MISSION_MANAGER_H
#define GS_MISSION_

#include "domain/d_mission.h"

#define DEFAULT_MISSION_DURATION (3600)

typedef struct D_MISSION_LITE_ELEM
{
    D_MISSION_LITE data;

    /** Next linked-list element */
    struct D_MISSION_LITE_ELEM* next;

    /** Mission that will be played after red team wons current mission */
    struct D_MISSION_LITE_ELEM* mNextRed;

    /** Mission that will be played after blue team wons current mission */
    struct D_MISSION_LITE_ELEM* mNextBlue;

    /** Mission that will be played after no team wons current mission or nextRead and nextBlue are unset */
    struct D_MISSION_LITE_ELEM* mNext;

    int refsCount;
} D_MISSION_LITE_ELEM;

static void mssn_set_current(D_MISSION_LITE_ELEM* value);
static void mssn_status_reset();
static void mssn_list_load();
static void mssn_load_weather_report(D_MISSION_LITE* mission);
static D_MISSION_LITE_ELEM* get_mssn_elem_by_name(char* name);
static void mssn_list_resolve_conflicts();
static void mssn_list_resolve_branch(char* name, void** nextRed, void** nextBlue, void** next);
static void mssn_list_save();
static void mssn_list_clear();
static void mssn_list_reload();
static void mssn_list_print();

void gs_mission_load();
void gs_mission_unload();

void gs_mission_run();
void gs_mission_end();
void gs_mission_rerun();

void gs_mission_next();
void gs_mission_prev();

void gs_mission_start();
void gs_mission_stop();
void gs_mission_restart();

void gs_mission_manager_init();
void gs_mission_manager_tearDown();

int gs_mission_seconds_left();

#endif // GS_MISSION_MANAGER_H
