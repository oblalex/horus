#ifndef GS_MISSION_MANAGER_H
#define GS_MISSION_

#include "domain/d_mission.h"
#include "util/common.h"

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

static void* mssn_timer_watcher();
static BOOL check_notificator_seconds(int* value, int range, int newValue);

void gs_mssn_load();
void gs_mssn_unload();

void gs_mssn_run();
void gs_mssn_end();
void gs_mssn_rerun();

void gs_mssn_next();
void gs_mssn_prev();

void gs_mssn_start();
void gs_mssn_stop();
void gs_mssn_restart();

void gs_mssn_manager_notify_loaded();
void gs_mssn_manager_notify_not_loaded();
void gs_mssn_manager_notify_running();

void gs_mssn_manager_init();
void gs_mssn_manager_tearDown();

void gs_mssn_time_str(char* str);
int gs_mssn_seconds_left();
void gs_mssn_seconds_left_set(int value);

#endif // GS_MISSION_MANAGER_H
