#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <time.h>
#include <string.h>

#include "gs.h"
#include "gs_scripts.h"
#include "gs_process.h"
#include "gs_cfg.h"
#include "gs_cmd.h"
#include "gs_console.h"
#include "gs_input_handlers.h"
#include "util/print_status.h"

static BOOL LAUNCHED_BEFORE = FALSE;
static BOOL DO_RUN = TRUE;
static BOOL LOADED;
static BOOL CONNECTED = FALSE;

void gs_init()
{
    gs_check_path_root();
	gs_setup_termination_hooks();
}

static void gs_setup_termination_hooks()
{
	signal(SIGPIPE,	SIG_IGN);

	signal(SIGINT,	gs_termination_handler);
	signal(SIGTERM,	gs_termination_handler);
	signal(SIGABRT,	gs_termination_handler);

	signal(SIGUSR1,	gs_loadded_hadler);
}

static void gs_termination_handler(int signum)
{		
	PRINT_STATUS_ORDER_RESET();
	wprintf(L"\n%s: #%d.\n", tr("Signal caugth"), signum);
	gs_exit();
}

static void gs_loadded_hadler()
{
	LOADED = TRUE;
}

void gs_run()
{
    char* gs_is_down_msg = tr("Game server is shut down");

    while (1) {
		LOADED = FALSE;

		gs_check_launched_before();
		if (DO_RUN == FALSE) break;

		gs_prepare();

		if (gs_process_create() == FALSE)
        {
			gs_on_process_stop();
            continue;
        }

		gs_on_process_start();

        if (DO_RUN == TRUE)
        {
            PRINT_STATUS_MSG_ERR_NOIND(gs_is_down_msg);
        } else {
            PRINT_STATUS_MSG(gs_is_down_msg);
            break;
        }
    }
}

static void gs_check_launched_before()
{
	if (LAUNCHED_BEFORE == TRUE)
	{
		char i;
		for(i=10; i>0; i--){
			if (DO_RUN == FALSE) break;
			int len = 40;
			char buf[len];
			sprintf(buf, "%s...%d", tr("Restarting game server in"), i);
			PRINT_STATUS_MSG_NOIND(&buf);
			sleep(1);
		}
	}
}

static void gs_prepare()
{
	gs_cfg();
	gs_scripts_generate();
	
	PRINT_STATUS_MULTI_START();
}

static void gs_on_process_start()
{
	gs_wait_loaded();

	if ((gs_is_running() == TRUE) && ((CONNECTED = gs_console_init()) == TRUE))
	{
			gs_cmd_init();
			input_handlers_start();
			gs_cmd_kick_all();
			gs_process_wait();
	} else {
		gs_process_kill();
	}
	gs_on_process_stop();
}

static void gs_wait_loaded()
{
	PRINT_STATUS_NEW(tr("Waiting for server is loaded"));
	
	int tries = 30;
	while ((LOADED == FALSE) && (DO_RUN == TRUE))
	{
		sleep(1);
		tries--;
		if (tries == 0)
		{
			PRINT_STATUS_MSG_ERR(tr("Game server loading timeout"));
			break;
		}
	}

	if (LOADED == TRUE)
	{
		PRINT_STATUS_DONE();
	} else {
		PRINT_STATUS_FAIL();
	}
}

void gs_exit()
{
	DO_RUN = FALSE;
	if (LOADED == TRUE)
	{ 
		gs_cmd_exit();
	} else {
		gs_process_kill();
	}
}

static void gs_on_process_stop()
{
	if (CONNECTED == TRUE)
	{
		input_handlers_stop();
		gs_cmd_tear_down();
	}
	gs_console_tear_down();
	
	PRINT_STATUS_MULTI_STOP();
	LAUNCHED_BEFORE = TRUE;
}

BOOL gs_is_running()
{
	return ((DO_RUN == TRUE) && (LOADED == TRUE)) ? TRUE : FALSE;
}
