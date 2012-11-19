#ifndef GS_PROCESS_H
#define GS_PROCESS_H

#include "util/common.h"

BOOL gs_process_create();
void gs_process_wait();
void gs_process_kill();

#endif // GS_PROCESS_H
