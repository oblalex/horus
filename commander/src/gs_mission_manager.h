#ifndef GS_MISSION_MANAGER_H
#define GS_MISSION_

#include "domain/d_mission.h"
#include "util/common.h"
#include "util/msg_queue.h"

#define MSSN_REQ_LOAD       (0)
#define MSSN_REQ_UNLOAD     (1)

#define MSSN_REQ_RUN        (2)
#define MSSN_REQ_END        (3)
#define MSSN_REQ_RERUN      (4)

#define MSSN_REQ_NEXT       (5)
#define MSSN_REQ_PREV       (6)

#define MSSN_REQ_START      (7)
#define MSSN_REQ_STOP       (8)
#define MSSN_REQ_RESTART    (9)

#define MSSN_REQ_TIME_LEFT_PRINT    (10)
#define MSSN_REQ_TIME_LEFT_SET      (11)

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
static void mssn_list_history_clear();
static void mssn_list_reload();
static void mssn_list_print();

static void msg_delete(MSG_T* msg);
static void msg_enqueue(MSG_T* msg);

static void* mssn_timer_watcher();
static void* mssn_msg_dispatcher();
static BOOL check_notificator_seconds(int* value, int range, int newValue);

static void* handle_events_in();

// Manager direct calls

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

// Manager requests

void gs_mssn_load_req();
void gs_mssn_unload_req();

void gs_mssn_run_req();
void gs_mssn_end_req();
void gs_mssn_rerun_req();

void gs_mssn_next_req();
void gs_mssn_prev_req();

void gs_mssn_start_req();
void gs_mssn_stop_req();
void gs_mssn_restart_req();

void gs_mssn_time_print_req();
void gs_mssn_time_left_set_req(int h, int m, int s);

// Manager notifications

void gs_mssn_manager_notify_loaded();
void gs_mssn_manager_notify_not_loaded();
void gs_mssn_manager_notify_running();

void gs_mssn_manager_init();
void gs_mssn_manager_tearDown();

void gs_mssn_time_print();
void gs_mssn_time_str(char* str);
int gs_mssn_seconds_left();
void gs_mssn_seconds_left_set(int value);
void gs_mssn_time_left_set(int h, int m, int s);
BOOL gs_mssn_running();

#endif // GS_MISSION_MANAGER_H
