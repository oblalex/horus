#ifndef GAME_SERVER_H
#define GAME_SERVER_H

#include <errno.h>
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>

#include "print_status.h"
#include "ini_helper.h"

#define GS_PATH PATH_PARENT PATH_SEP

#define GS_LOGS_DIR "logs" PATH_SEP
#define GS_LOGS_DIR_PATH GS_PATH GS_LOGS_DIR

#define GS_LOG_STD "sconsole" FILE_EXT_LOG
#define GS_LOG_STD_PATH GS_LOGS_DIR_PATH GS_LOG_STD

#define GS_LOG_ERR "serrors" FILE_EXT_LOG
#define GS_LOG_ERR_PATH GS_LOGS_DIR_PATH GS_LOG_ERR

#define GS_LOG_EVT "sevents" FILE_EXT_LOG
#define GS_LOG_EVT_PATH_REL GS_LOGS_DIR GS_LOG_EVT
const char *const GS_LOG_EVT_PATH = GS_PATH GS_LOG_EVT_PATH_REL;

#define GS_EXE "il2server" FILE_EXT_EXE
#define GS_EXE_PATH_def GS_PATH GS_EXE
const char *const GS_EXE_PATH = GS_EXE_PATH_def;

#define GS_OUT_REDIRECT ">" GS_LOG_STD_PATH " 2>" GS_LOG_ERR_PATH
const char *const GS_START = "wine " GS_EXE_PATH_def GS_OUT_REDIRECT;

#define GS_CFG "confs" FILE_EXT_INI
const char *const GS_CFG_PATH = GS_PATH GS_CFG;

#define GS_CMDF_MAIN "server" FILE_EXT_CMD
const char *const GS_CMDF_MAIN_PATH = GS_PATH GS_CMDF_MAIN;

#define GS_CMDF_GC "gc" FILE_EXT_CMD
const char *const GS_CMDF_GC_PATH = GS_PATH GS_CMDF_GC;

const char *const GS_CONSOLE_PORT = "20001";
const char *const GS_CONSOLE_CLIENT_ADDR = "127.0.0.1";

#define GS_CMD_GC "GC"
#define GS_CMD_TIMEOUT(DELAY, CMD) "timeout " DELAY " " CMD
#define GS_CMD_RUN_FILE(FNAME) "f " FNAME

// configuration sections names
const char *const GS_CFG_CHAT	= "chat";
const char *const GS_CFG_GAME	= "game";
const char *const GS_CFG_CONSOLE = "Console";
const char *const GS_CFG_NET	= "NET";

void gs_start();
void gs_stop();
void* gs_keep_running();
void gs_create_process();
void gs_wait_loaded();
void gs_wait_process_finished();

void gs_init();
void gs_descriptor_init();
void gs_check_paths();
void gs_check_path_root();
void gs_check_path_logs();

void gs_check_settings();
void gs_scripts_generate();

void gs_config(INI_CONTAINER* cfg);
void gs_config_logging(INI_CONTAINER* cfg);
void gs_config_logging_chat(INI_CONTAINER* cfg);
void gs_config_logging_console(INI_CONTAINER* cfg);
void gs_config_logging_file(INI_CONTAINER* cfg);
void gs_config_console_connection(INI_CONTAINER* cfg);
void gs_config_version_checking(INI_CONTAINER* cfg);

void gs_scripts_generate_gc();
void gs_scripts_generate_main();

typedef struct gs_descriptor
{
	pthread_t runner;
	pid_t pid;
	BOOL do_run;
	BOOL loaded;
} GSIPTOR;

GSIPTOR GS;

void gs_start()
{
	if (GS.pid > (pid_t) 0)
	{	
		return;
	}

	PRINT_STATUS_NEW("Starting game server");

	gs_init();	
	pthread_create(&(GS.runner), NULL, gs_keep_running, (void*) NULL);

	while(GS.loaded == FALSE){
		waitMs(0, 100);
	}

	PRINT_STATUS_DONE();
}

void gs_stop()
{
	if (GS.pid <= (pid_t) 0)
	{	
		return;
	}

	PRINT_STATUS_NEW("Stopping game server");
	
	GS.do_run = FALSE;
	
	// TODO: if connected to console, send "exit" to server, else kill process

	kill(GS.pid, SIGSTOP);
	GS.pid = (pid_t) NULL;

	PRINT_STATUS_DONE();
}

void* gs_keep_running()
{
	BOOL was_launched_earlier = FALSE;
	char* gs_is_down_msg = "Game server is shut down";

	while (1) {
		if (was_launched_earlier == TRUE)
		{
			PRINT_STATUS_MSG_NOIND("Restarting game server");
			waitMs(2, 0);
		}
		
		gs_create_process();
		if (GS.pid <= (pid_t) 0)
		{
			was_launched_earlier = TRUE;
			continue;
		}
		
		gs_wait_loaded();		
		gs_wait_process_finished();

		if (GS.do_run == TRUE)
		{
			PRINT_STATUS_MSG_ERR_NOIND(gs_is_down_msg);
			was_launched_earlier = TRUE;
		} else {
			PRINT_STATUS_MSG(gs_is_down_msg);
			break;
		}
	}

	return NULL;
}

void gs_create_process()
{
	PRINT_STATUS_NEW("Creating game server process");

	if ((GS.pid = fork()) < 0)
	{
		PRINT_STATUS_MSG_ERR("Failed to fork");
		PRINT_STATUS_FAIL();
        return;
	} else if (GS.pid == 0) {
		signal(SIGINT,	SIG_DFL);
		signal(SIGKILL,	SIG_DFL);
		signal(SIGTERM,	SIG_DFL);
		signal(SIGABRT,	SIG_DFL);
		signal(SIGCHLD, SIG_DFL);

		execl("/bin/sh", "sh", "-c", GS_START, (char*) 0);
		_exit(127);
	}

	PRINT_STATUS_DONE();
}

void gs_wait_loaded()
{
	PRINT_STATUS_NEW("Waiting for server is loaded");

	waitMs(1, 0);

	FILE* f_log = fopen (GS_LOG_STD_PATH, "rt");  	
  	if (f_log == NULL)
  	{
  		PRINT_STATUS_MSG_ERR("Unable to open server's output file \"" GS_LOG_STD "\"");
		PRINT_STATUS_FAIL();
		gs_stop();
  		exit(EXIT_FAILURE);
  	}
  	
  	int line_len = 70;
	char line[line_len];

  	while(GS.loaded == FALSE){
  		if (fgets(line, 80, f_log) == NULL)
  		{
  			waitMs(0, 300);
  		} else if (strstr(line, "IL2 FB dedicated server") != NULL){
  			GS.loaded = TRUE;
  		}
  	}
  	
	fclose (f_log);
	PRINT_STATUS_DONE();
}

void gs_wait_process_finished()
{
	int childExitStatus;
	waitpid(GS.pid, &childExitStatus, 0);

	if(WIFEXITED(childExitStatus) == 0)
	{
		char err_msg [70];
		sprintf (err_msg, "waitpid() exited with an error. Status=%d", WEXITSTATUS(childExitStatus));
   		PRINT_STATUS_MSG_ERR(err_msg);
	} else if (WIFSIGNALED(childExitStatus)) {
		char err_msg [60];
		sprintf (err_msg, "waitpid() exited due to a signal: %d", WTERMSIG(childExitStatus));
   		PRINT_STATUS_MSG_ERR(err_msg);
	}

	GS.loaded = FALSE;
}

void gs_init(){
	PRINT_STATUS_NEW("Initialization");

	gs_descriptor_init();
	gs_check_paths();
	gs_check_settings();
	gs_scripts_generate();

	PRINT_STATUS_DONE();
}

void gs_descriptor_init()
{
	GS.do_run = TRUE;
	GS.loaded = FALSE;
	GS.runner = (pthread_t) NULL;
	GS.pid = (pid_t) NULL;
}

void gs_check_paths()
{
	PRINT_STATUS_NEW("Checking server's paths");
	
	gs_check_path_root();
	gs_check_path_logs();

	PRINT_STATUS_DONE();
}

void gs_check_path_root()
{
	PRINT_STATUS_NEW("Checking root path");

	if (access(GS_EXE_PATH, X_OK) != -1) {
		PRINT_STATUS_DONE();
	} else {
		PRINT_STATUS_MSG_ERR("Please, make sure in following:");
		PRINT_STATUS_MSG_ERR("1. This program is located in IL-2 server's subdirectory.");
		PRINT_STATUS_MSG_ERR("2. You are located at that directory currently.");
		PRINT_STATUS_MSG_ERR("3. IL-2 server's executable file is named as \"" GS_EXE "\".");
		PRINT_STATUS_FAIL();
		exit(EXIT_FAILURE);
	}
}

void gs_check_path_logs()
{
	PRINT_STATUS_NEW("Checking logs path");
	
	struct stat st;
	char* path = GS_LOGS_DIR_PATH;

	if ((stat(path, &st) != 0) && (errno == ENOENT))
	{
		PRINT_STATUS_MSG("Creating missing directory");
		if (mkdir(path, S_IRWXU) != 0)
		{
			char err_msg[sizeof(GS_LOGS_DIR_PATH)+20+1];
			strcpy (err_msg, "Failed to create \"");
			strcat (err_msg, path);
			strcat (err_msg, "\"");

			PRINT_STATUS_MSG_ERR(err_msg);
			PRINT_STATUS_FAIL();
			exit(EXIT_FAILURE);
		}
	}
	
	PRINT_STATUS_DONE();
}

void gs_check_settings()
{
	PRINT_STATUS_NEW("Checking server's configuration");
	
	PRINT_STATUS_MSG("Loading configuration");
	INI_CONTAINER* cfg = ini_start((char*)GS_CFG_PATH);
	
	const char* err_msg = NULL;
	
	while(1)
	{
		if (cfg == NULL) {
			err_msg = INI_MALLOC_ERR_CONTAINER;
			break;
		}		
		if ((err_msg = cfg->error_msg) != NULL) break;
		
		gs_config(cfg);
		
		PRINT_STATUS_MSG("Saving configuration");
		ini_end(cfg);		
		err_msg = cfg->error_msg;
		break;
	}

	if (err_msg == NULL) {
		PRINT_STATUS_DONE();
	} else {
		PRINT_STATUS_MSG_ERR(err_msg);
		PRINT_STATUS_FAIL();
		exit(EXIT_FAILURE);
	}
}

void gs_config(INI_CONTAINER* cfg)
{
	gs_config_logging(cfg);
	gs_config_console_connection(cfg);
	gs_config_version_checking(cfg);
}

void gs_config_logging(INI_CONTAINER* cfg)
{
	gs_config_logging_chat(cfg);
	gs_config_logging_console(cfg);	
	gs_config_logging_file(cfg);
}

void gs_config_logging_chat(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Suppressing logging to chat");
	ini_value_set(cfg, GS_CFG_CHAT, "autoLogDetail", "0");
}

void gs_config_logging_console(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Disabling console output saving to file");
	ini_value_set(cfg, GS_CFG_CONSOLE, "LOG", "0");
}

void gs_config_logging_file(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Setting logging output file");
	ini_value_set(cfg, GS_CFG_GAME, "eventlog", GS_LOG_EVT_PATH_REL);
	
	PRINT_STATUS_MSG("Enabling log resetting for every mission");
	ini_value_set(cfg, GS_CFG_GAME, "eventlogkeep", "0");
	
	PRINT_STATUS_MSG("Enabling buildings destruction logging");
	ini_value_set(cfg, GS_CFG_GAME, "eventlogHouse", "1");
}

void gs_config_console_connection(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Setting console port");
	ini_value_set(cfg, GS_CFG_CONSOLE, "IP", GS_CONSOLE_PORT);

	PRINT_STATUS_MSG("Setting console allowed client address");
	ini_value_set(cfg, GS_CFG_CONSOLE, "IPS", GS_CONSOLE_CLIENT_ADDR);
}

void gs_config_version_checking(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Setting client version checking");
	ini_value_set(cfg, GS_CFG_NET, "checkRuntime", "1");
}

void gs_scripts_generate()
{
	PRINT_STATUS_NEW("Generating server's scripts");

	gs_scripts_generate_gc();
	gs_scripts_generate_main();
	
	PRINT_STATUS_DONE();
}

void gs_scripts_generate_gc()
{
	PRINT_STATUS_NEW("Generating garbage collecting script");

	FILE* f_gc = fopen (GS_CMDF_GC_PATH, "w");  	
  	if (f_gc == NULL)
  	{
  		PRINT_STATUS_MSG_ERR("Unable to create file \"" GS_CMDF_GC "\"");
		PRINT_STATUS_FAIL();
  		exit(EXIT_FAILURE);
  	}
  	
  	fputs(GS_CMD_GC "\n", f_gc);
  	fputs(GS_CMD_GC "\n", f_gc);
  	fputs(GS_CMD_GC "\n", f_gc);
  	fputs(
  		GS_CMD_TIMEOUT(
  			"3600000",
  			GS_CMD_RUN_FILE(GS_CMDF_GC)) "\n", f_gc);
	fclose (f_gc);
	PRINT_STATUS_DONE();
}

void gs_scripts_generate_main()
{
	PRINT_STATUS_NEW("Generating main script");

	FILE* f_sys = fopen (GS_CMDF_MAIN_PATH, "w");  	
  	if (f_sys == NULL)
  	{
  		PRINT_STATUS_MSG_ERR("Unable to create file \"" GS_CMDF_MAIN "\"");
		PRINT_STATUS_FAIL();
  		exit(EXIT_FAILURE);
  	}
  	
  	fputs (GS_CMD_RUN_FILE(GS_CMDF_GC) "\n", f_sys);
	fclose (f_sys);
	PRINT_STATUS_DONE();
}

#endif // GAME_SERVER_H