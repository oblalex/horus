#include <stdlib.h>
#include <signal.h>
#include <time.h>
#include <string.h>

#ifndef _WIN_
    #include <unistd.h>
#endif

#include "gs.h"
#include "gs_paths.h"
#include "gs_scripts.h"
#include "gs_process.h"
#include "gs_cfg.h"
#include "sys_cfg.h"
#include "gs_cmd.h"
#include "gs_console.h"
#include "gs_input_handlers.h"
#include "gs_mission_manager.h"
#include "console_parser.h"
#include "pilot_manager.h"
#include "shell_parser.h"
#include "util/print_status.h"

static void gs_setup_termination_hooks();
static void gs_termination_handler(int signum);

static void gs_check_launched_before();
static void gs_prepare();
static void gs_wait_loaded();
static void gs_on_process_start();
static void gs_on_process_stop();

static BOOL LAUNCHED_BEFORE = FALSE;
static BOOL DO_RUN = TRUE;
static BOOL LOADED;
static BOOL CONNECTED = FALSE;

void gs_init()
{
    gs_check_path_root();
	gs_setup_termination_hooks();
    gs_mssn_manager_init();
	
#if !defined(_WIN_)
	signal(SIGUSR1, gs_set_loadded);
#endif
}

static void gs_setup_termination_hooks()
{
	BOOL shLock = FALSE;
    PRINT_STATUS_NEW(tr("Setting up signal hooks"), shLock);

	#if !defined(_WIN_)
	signal(SIGPIPE,	SIG_IGN);
	#endif

	signal(SIGINT,	gs_termination_handler);
	signal(SIGTERM,	gs_termination_handler);
	signal(SIGABRT,	gs_termination_handler);

    PRINT_STATUS_DONE(shLock);
}

static void gs_termination_handler(int signum)
{		
	PRINT_STATUS_ORDER_RESET();
#ifdef _WIN_
    char buf[40];
    CharToOem(tr("Signal caugth"), buf);
    wprintf(L"\n%S: #%d.\n", buf, signum);
#else
    wprintf(L"\n%s: #%d.\n", tr("Signal caugth"), signum);
#endif
	gs_exit();
}

void gs_set_loadded()
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
            PRINT_STATUS_MSG_ERR_NOIND(gs_is_down_msg, TRUE);
        } else {
            PRINT_STATUS_MSG(gs_is_down_msg, TRUE);
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
            char buf[40];
			sprintf(buf, "%s...%d", tr("Restarting game server in"), i);
            PRINT_STATUS_MSG_NOIND((char*)(&buf), TRUE);
			
        #if defined(_WIN_)
			Sleep(1000);
        #else
			sleep(1);
        #endif
		}
	}
}

static void gs_prepare()
{
    gs_cfg_init();
    sys_cfg_init();
	gs_scripts_generate();
	
	PRINT_STATUS_MULTI_START();
}

static void gs_on_process_start()
{
	gs_wait_loaded();

	if ((gs_is_running() == TRUE) && ((CONNECTED = gs_console_init()) == TRUE))
	{
            pm_init();
            console_parser_init();
            gs_cmd_init();
            input_handlers_start();
            gs_cmd_kick_all();
            gs_mssn_start();
            gs_process_wait();
	} else {
		gs_process_kill();
	}
	gs_on_process_stop();
}

static void gs_wait_loaded()
{
	BOOL shLock = FALSE;
	PRINT_STATUS_NEW(tr("Waiting for server is loaded"), shLock);
	
	int tries = 30;
	while ((LOADED == FALSE) && (DO_RUN == TRUE))
	{
		#if defined(_WIN_)
		Sleep(1000);
		#else
		sleep(1);
		#endif
			
		tries--;
		if (tries == 0)
		{
			PRINT_STATUS_MSG_ERR(tr("Game server loading timeout"), shLock);
			break;
		}
	}

	if (LOADED == TRUE)
	{
		PRINT_STATUS_DONE(shLock);
	} else {
		PRINT_STATUS_FAIL(shLock);
	}
}

void gs_exit()
{
    DO_RUN = FALSE;
	if (LOADED == TRUE)
    {
        gs_mssn_manager_tearDown();
		gs_cmd_exit();
	} else {
		gs_process_kill();
	}
}

static void gs_on_process_stop()
{
	if (CONNECTED == TRUE)
	{
        gs_mssn_manager_tearDown();
		input_handlers_stop();
		gs_cmd_tear_down();
        console_parser_teardown();
        pm_teardown();
    }

	gs_console_tear_down();
    sys_cfg_teardown();
	
	PRINT_STATUS_MULTI_STOP();
	LAUNCHED_BEFORE = TRUE;
}

BOOL gs_is_running()
{
	return ((DO_RUN == TRUE) && (LOADED == TRUE)) ? TRUE : FALSE;
}
