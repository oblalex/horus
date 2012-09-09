#ifndef GS_PROCESS_H
#define GS_PROCESS_H

#include "util/common.h"

BOOL gs_process_create();

#if !defined(_WIN_)
static void* gs_process_create_raw();
#endif

static void gs_wait_loaded();
static void gs_suppress_stdout();
void gs_process_wait();
void gs_process_kill();

#endif // GS_PROCESS_H
