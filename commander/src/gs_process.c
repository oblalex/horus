#include "gs_process.h"

#include <config.h>

#if defined(_WIN_)
	#include <windows.h>
#endif

#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <time.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <pthread.h>

#include "gs.h"

#include "gs_paths.h"
#include "util/file.h"
#include "util/l10n.h"
#include "util/print_status.h"

static pid_t GS_PID = (pid_t) 0;

BOOL gs_process_create()
{
	PRINT_STATUS_NEW(tr("Creating game server process"));

	if ((GS_PID = fork()) < 0)
	{
		PRINT_STATUS_MSG_ERR(tr("Failed to fork"));
		PRINT_STATUS_FAIL();
		return FALSE;
	} else if (GS_PID == 0) {
		signal(SIGINT,	SIG_IGN);
		signal(SIGTERM,	SIG_IGN);
		signal(SIGABRT,	SIG_IGN);

		pthread_t thrd;
		pthread_create(&thrd, NULL, &gs_process_create_raw, NULL);
		gs_wait_loaded();
		pthread_join(thrd, NULL);
		_exit(127);
	}

	PRINT_STATUS_DONE();
	return TRUE;
}

static void* gs_process_create_raw()
{
	pid_t pid;
	if ((pid=fork())==0)
	{
		char* cmd = "wine " PATH_GS_EXE ">" PATH_GS_STDOUT " 2>" PATH_GS_LOG_ERR;
		execl("/bin/sh", "sh", "-c", cmd, (char*) 0);
		_exit(127);
	}

	int childExitStatus;
	waitpid(pid, &childExitStatus, 0);
	return NULL;
}

static void gs_wait_loaded()
{
	sleep(2);

	int line_len = 64;
	char line[line_len];
	int offset = 0;
	RL_STAT stat;

	int o_flags = O_RDONLY;
	
	#if !defined(_WIN_)
		o_flags |= O_NONBLOCK;
	#endif
	
	int stream = open(PATH_GS_STDOUT, o_flags);
	while(1)
	{
		line_rd(stream, line, line_len, offset, &stat);
		if (stat.finished == FALSE)
		{
			offset += stat.length;
			usleep(500*1000);
		} else {
			offset = 0;
			if (strstr(line, "1>") != NULL)
			{
				gs_suppress_stdout();
				
				#if !defined(_WIN_)
				kill(getppid(), SIGUSR1);
				#else
				SendMessage(gs_getSigUsrWindow(), WM_USER, 0, 0);
				#endif
				
				break;
			}
		}
	}
	close(stream);
	unlink(PATH_GS_STDOUT);
}

static void gs_suppress_stdout()
{
	int newOut = open(DEV_NULL, O_WRONLY);
	dup2(newOut, STDOUT_FILENO);
	close(newOut);
}

void gs_process_wait()
{
	int childExitStatus;
	waitpid(GS_PID, &childExitStatus, 0);

	PRINT_STATUS_MSG(tr("Game server's process finished"));

	GS_PID = (pid_t) 0;
}

void gs_process_kill()
{
	if (GS_PID) kill(GS_PID, SIGKILL);
}
