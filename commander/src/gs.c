#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <time.h>

#include "gs.h"
#include "gs_paths.h"
#include "gs_scripts.h"
#include "gs_cfg.h"
#include "gs_fifo.h"
#include "gs_cmd.h"
#include "gs_input_handlers.h"
#include "util/print_status.h"
#include "util/file.h"

GS_DESCRIPTOR GS;

void gs_init()
{
    gs_check_path_root();
	gs_descriptor_init();
	gs_setup_termination_hooks();
}

void gs_descriptor_init()
{
	GS.pid = (pid_t) 0;

	GS.in = GS.out = 0;

	GS.loaded = GS.launched_before = FALSE;
	GS.do_run = TRUE;
}

void gs_setup_termination_hooks()
{
	signal(SIGPIPE,	SIG_IGN);

	signal(SIGINT,	gs_termination_handler);
	signal(SIGTERM,	gs_termination_handler);
	signal(SIGABRT,	gs_termination_handler);
}

void gs_termination_handler(int signum)
{		
	PRINT_STATUS_ORDER_RESET();
	wprintf(L"\n%s: #%d.\n", tr("Signal caugth"), signum);
	gs_exit();
}

void gs_run()
{
    char* gs_is_down_msg = tr("Game server is shut down");

    while (1) {
		gs_check_launched_before();
		if (GS.do_run == FALSE) break;

		if (gs_prepare() == FALSE)
		{
            GS.launched_before = TRUE;
            continue;
		}
		
		gs_process_create();
		if (GS.pid <= (pid_t) 0)
        {
			gs_on_process_stop();
            continue;
        }

		if (gs_fifos_open(&(GS.in), &(GS.out)) == FALSE)
		{
			gs_process_kill();
            gs_on_process_stop();
            continue;
		}
		
		gs_on_process_start();

        if (GS.do_run == TRUE)
        {
            PRINT_STATUS_MSG_ERR_NOIND(gs_is_down_msg);
        } else {
            PRINT_STATUS_MSG(gs_is_down_msg);
            break;
        }
    }
}

void gs_check_launched_before()
{
	if (GS.launched_before == TRUE)
	{
		char i;
		for(i=5; i>0; i--){
			if (GS.do_run == FALSE) break;
			wchar_t buf[40];
			sprintf(buf, "%s...%d", tr("Restarting game server in"), i);
			PRINT_STATUS_MSG_NOIND(buf);
			sleep(1);
		}
	}
}

BOOL gs_prepare()
{
	gs_cfg();
	gs_scripts_generate();
	return gs_fifos_create();
}

void gs_process_create()
{
    PRINT_STATUS_NEW("Creating game server process");

    if ((GS.pid = fork()) < 0)
    {
        PRINT_STATUS_MSG_ERR("Failed to fork");
        PRINT_STATUS_FAIL();
        return;
    } else if (GS.pid == 0) {
        signal(SIGINT,	SIG_IGN);
        signal(SIGTERM,	SIG_IGN);
        signal(SIGABRT,	SIG_IGN);

		char cmd[80];
		sprintf(cmd, "wine %s<%s >>%s 2>%s", PATH_GS_EXE, PATH_GS_STDIN, PATH_GS_STDOUT, PATH_GS_LOG_ERR);

        execl("/bin/sh", "sh", "-c", cmd, (char*) 0);
        _exit(127);
    }

    PRINT_STATUS_DONE();
}

void gs_on_process_start()
{
	gs_cmd_init(GS.in);
	gs_wait_loaded();
	input_handlers_init(GS.out);
	input_handlers_start();
	gs_process_wait();
	gs_on_process_stop();
}

void gs_wait_loaded()
{
    PRINT_STATUS_NEW("Waiting for server is loaded");

    int line_len = 64;
    char line[line_len];
	int offset = 0;
	RL_STAT stat;

    while(GS.do_run == TRUE)
	{
		line_rd(GS.out, line, line_len, offset, &stat);
        if (stat.finished == FALSE)
        {
			offset += stat.length;
            usleep(300*1000);
        } else {
			offset = 0;
			if (strstr(line, "1>") != NULL)
			{
            	GS.loaded = TRUE;
				break;
			}
        }
    }

	if (GS.do_run == TRUE)
	{
		PRINT_STATUS_DONE();
	} else {
		PRINT_STATUS_FAIL();
	}
}

void gs_process_wait()
{
    int childExitStatus;
    waitpid(GS.pid, &childExitStatus, 0);

	PRINT_STATUS_MSG("Game server's process finished");

	GS.pid = (pid_t) 0;
    GS.loaded = FALSE;
}

void gs_exit()
{
	GS.do_run = FALSE;
	if (GS.loaded == TRUE)
	{ 
		gs_cmd_exit();
	} else {
		gs_process_kill();
	}
}

void gs_process_kill()
{
	if (GS.pid) kill(GS.pid, SIGKILL);
}

void gs_on_process_stop()
{
	gs_fifos_dispose(&(GS.in), &(GS.out));
	input_handlers_stop();
	GS.launched_before = TRUE;
}

BOOL gs_is_running()
{
	return ((GS.do_run == TRUE) && (GS.loaded == TRUE)) ? TRUE : FALSE;
}
