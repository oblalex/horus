#ifndef GS_H
#define GS_H

#include <stdio.h>
#include <sys/types.h>

#include "util/common.h"

typedef struct
{
    pid_t pid;
	
	int in, out;

	BOOL launched_before;
	BOOL loaded;
    BOOL do_run;
} GS_DESCRIPTOR;

void gs_init();
void gs_descriptor_init();
void gs_setup_termination_hooks();
void gs_termination_handler(int signum);

void gs_run();
void gs_check_launched_before();
BOOL gs_prepare();
void gs_process_create();
void gs_on_process_start();
void gs_wait_loaded();
void gs_on_process_stop();
void gs_process_wait();

void gs_exit();
void gs_process_kill();

BOOL gs_is_running();

#endif // GS_H

