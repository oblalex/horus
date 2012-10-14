#include "gs_process.h"

#include <config.h>

#if defined(_WIN_)
	#include <windows.h>
	#include "util/winproc.h"
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

#if defined(_WIN_)

static PROCESS_INFORMATION GS_PINF;
static HANDLE GS_HANDLE_OUT;
static HANDLE GS_HANDLE_ERR;

BOOL gs_process_create()
{
	PRINT_STATUS_NEW(tr("Creating game server process"));

	/*
	GS_HANDLE_ERR = GetStdHandle(STD_ERROR_HANDLE);
	
	SECURITY_ATTRIBUTES sec;
	sec.bInheritHandle = TRUE;
	
	GS_HANDLE_OUT = CreateFile(
		PATH_GS_STDOUT,
		GENERIC_READ | GENERIC_WRITE,
		FILE_SHARE_READ,
		&sec,
		CREATE_ALWAYS,
		FILE_ATTRIBUTE_NORMAL,
		NULL);

	if(GS_HANDLE_OUT == INVALID_HANDLE_VALUE)
	{
		printf("%d\n", GetLastError());
	}
	*/
		
	STARTUPINFO si;
	ZeroMemory(&si, sizeof(si));
    si.cb = sizeof(si);
	//si.hStdError = GS_HANDLE_ERR;
	//si.hStdOutput = GS_HANDLE_OUT;
	//si.dwFlags |= STARTF_USESTDHANDLES;
	
	ZeroMemory(&GS_PINF, sizeof(GS_PINF));
	
    char* cmd = "cmd /c " PATH_GS_EXE "< NUL >" PATH_GS_STDOUT " 2>" PATH_GS_LOG_ERR;
	
	 if( CreateProcess(
		NULL,
        cmd,
        NULL,
        NULL,
        FALSE,
        0,
        NULL,
        NULL,
        &si,
        &GS_PINF) == 0)
    {
        PRINT_STATUS_FAIL();
		return FALSE;
    }
	
	gs_wait_loaded();
	
	PRINT_STATUS_DONE();
	return TRUE;
}
#else

static pid_t GS_PID = (pid_t) 0;

BOOL gs_process_create()
{
	PRINT_STATUS_NEW(tr("Creating game server process"));

	GS_PID = fork();
	
	if (GS_PID < 0)
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
	pid_t pid = fork();
	
	if (pid==0)
	{
		char* cmd = "wine " PATH_GS_EXE ">" PATH_GS_STDOUT " 2>" PATH_GS_LOG_ERR;
		execl("/bin/sh", "sh", "-c", cmd, (char*) 0);
		_exit(127);
	}

	int childExitStatus;
	waitpid(pid, &childExitStatus, 0);
	
	return NULL;
}
#endif

static void gs_wait_loaded()
{
	#if defined(_WIN_)
	Sleep(2000);
	#else
	sleep(2);
	#endif

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
				
			#if defined(_WIN_)
				gs_set_loadded();
			#else
				kill(getppid(), SIGUSR1);
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
#if defined(_WIN_)
	// todo:
#else
	int newOut = open(DEV_NULL, O_WRONLY);
	dup2(newOut, STDOUT_FILENO);
	close(newOut);
#endif
}

void gs_process_wait()
{
#if defined(_WIN_)
    WaitForSingleObject(GS_PINF.hProcess, INFINITE);
    CloseHandle(GS_PINF.hProcess);
    CloseHandle(GS_PINF.hThread);
#else
	int childExitStatus;
	waitpid(GS_PID, &childExitStatus, 0);
	GS_PID = (pid_t) 0;
#endif
	PRINT_STATUS_MSG(tr("Game server's process finished"));
}

void gs_process_kill()
{
#if defined(_WIN_)
	DWORD dwExitCode = 0;
	GetExitCodeProcess(GS_PINF.hProcess, &dwExitCode);
	TerminateProcess(GS_PINF.hProcess, dwExitCode);
#else
	kill(GS_PID, SIGKILL);
#endif
}
