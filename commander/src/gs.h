#ifndef GS_H
#define GS_H

#include <config.h>

#ifdef _WIN_
	#include <windows.h>
#endif

#include <stdio.h>
#include <sys/types.h>

#include "util/common.h"

void gs_init();
void gs_set_loadded();
void gs_run();
void gs_exit();
BOOL gs_is_running();

#endif // GS_H

