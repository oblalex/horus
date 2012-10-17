#ifndef GS_MISSION_MANAGER_H
#define GS_MISSION_MANAGER_H

static void mission_status_reset();
static void mission_list_load();
static void mission_list_save();
static void mission_list_clear();
static void mission_list_reload();

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

void gs_mission_fullstop();

int gs_mission_seconds_left();

#endif // GS_MISSION_MANAGER_H
