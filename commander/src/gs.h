#ifndef GS_H
#define GS_H

#include <stdio.h>
#include <sys/types.h>

#include "util/common.h"

void gs_init();
static void gs_setup_termination_hooks();
static void gs_termination_handler(int signum);
static void gs_loadded_hadler();

void gs_run();
static void gs_check_launched_before();
static void gs_prepare();
static void gs_wait_loaded();
static void gs_on_process_start();
static void gs_on_process_stop();

void gs_exit();

BOOL gs_is_running();

#endif // GS_H

