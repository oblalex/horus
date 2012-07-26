#ifndef GAME_SERVER_H
#define GAME_SERVER_H

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

#include "print_status.h"
#include "ini_helper.h"

#define GAME_SERVER_PATH PATH_PARENT PATH_SEP

#define GAME_SERVER_EXE "il2server.exe"
const char *const GAME_SERVER_EXE_PATH = GAME_SERVER_PATH GAME_SERVER_EXE;

#define GAME_SERVER_CFG "confs.ini"
const char *const GAME_SERVER_CFG_PATH = GAME_SERVER_PATH GAME_SERVER_CFG;

#define GAME_SERVER_LOG "eventlog.lst"
const char *const GAME_SERVER_LOG_PATH = GAME_SERVER_PATH GAME_SERVER_LOG;

#define GAME_SERVER_CMDF_MAIN "server.cmd"
const char *const GAME_SERVER_CMDF_MAIN_PATH = GAME_SERVER_PATH GAME_SERVER_CMDF_MAIN;

#define GAME_SERVER_CMDF_GC "gc.cmd"
const char *const GAME_SERVER_CMDF_GC_PATH = GAME_SERVER_PATH GAME_SERVER_CMDF_GC;

#define GAME_SERVER_CMD_GC "GC"
#define GAME_SERVER_CMD_TIMEOUT(DELAY, CMD) "timeout " DELAY " " CMD
#define GAME_SERVER_CMD_RUN_FILE(FNAME) "f " FNAME

// configuration sections names
const char *const GAME_SERVER_CFG_CHAT	= "chat";
const char *const GAME_SERVER_CFG_GAME	= "game";
const char *const GAME_SERVER_CFG_CONSOLE = "Console";
const char *const GAME_SERVER_CFG_NET	= "NET";

void game_server_init();
void game_server_check_path();
void game_server_check_settings();
void game_server_scripts_generate();

void game_server_config(INI_CONTAINER* cfg);
void game_server_config_logging(INI_CONTAINER* cfg);
void game_server_config_logging_chat(INI_CONTAINER* cfg);
void game_server_config_logging_console(INI_CONTAINER* cfg);
void game_server_config_logging_file(INI_CONTAINER* cfg);
void game_server_config_security(INI_CONTAINER* cfg);

void game_server_scripts_generate_gc();
void game_server_scripts_generate_main();

void game_server_init(){
	game_server_check_path();
	game_server_check_settings();
	game_server_scripts_generate();
}

void game_server_check_path()
{
	PRINT_STATUS_NEW("Checking server's executable file path");

	if (access(GAME_SERVER_EXE_PATH, X_OK) != -1) {
		PRINT_STATUS_DONE();
	} else {
		PRINT_STATUS_MSG_ERR("Please, make sure in following:");
		PRINT_STATUS_MSG_ERR("1. This program is located in IL-2 server's subdirectory.");
		PRINT_STATUS_MSG_ERR("2. You are located at that directory currently.");
		PRINT_STATUS_MSG_ERR("3. IL-2 server's executable file is named as \"" GAME_SERVER_EXE "\".");
		PRINT_STATUS_FAIL();
		exit(EXIT_FAILURE);
	}
}

void game_server_check_settings()
{
	PRINT_STATUS_NEW("Checking server's configuration");
	
	PRINT_STATUS_MSG("Loading configuration");
	INI_CONTAINER* cfg = ini_start((char*)GAME_SERVER_CFG_PATH);
	
	const char* err_msg = NULL;
	
	while(1)
	{
		if (cfg == NULL) {
			err_msg = INI_MALLOC_ERR_CONTAINER;
			break;
		}		
		if ((err_msg = cfg->error_msg) != NULL) break;
		
		game_server_config(cfg);		
		if ((err_msg = cfg->error_msg) != NULL) break;
		
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

void game_server_config(INI_CONTAINER* cfg)
{
	game_server_config_logging(cfg);	
	if (ini_has_err(cfg) == TRUE) return;
	
	game_server_config_security(cfg);
}

void game_server_config_logging(INI_CONTAINER* cfg)
{
	game_server_config_logging_chat(cfg);
	if (ini_has_err(cfg) == TRUE) return;
	
	game_server_config_logging_console(cfg);
	if (ini_has_err(cfg) == TRUE) return;
	
	game_server_config_logging_file(cfg);
}

void game_server_config_logging_chat(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Suppressing logging to chat");
	ini_value_set(cfg, GAME_SERVER_CFG_CHAT, "autoLogDetail", "0");
}

void game_server_config_logging_console(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Disabling console output saving to file");
	ini_value_set(cfg,GAME_SERVER_CFG_CONSOLE, "LOG", "0");
}

void game_server_config_logging_file(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Setting logging output file");
	ini_value_set(cfg, GAME_SERVER_CFG_GAME, "eventlog", GAME_SERVER_LOG);
	if (ini_has_err(cfg) == TRUE) return;
	
	PRINT_STATUS_MSG("Enabling log resetting for every mission");
	ini_value_set(cfg, GAME_SERVER_CFG_GAME, "eventlogkeep", "0");
	if (ini_has_err(cfg) == TRUE) return;
	
	PRINT_STATUS_MSG("Enabling buildings destruction logging");
	ini_value_set(cfg, GAME_SERVER_CFG_GAME, "eventlogHouse", "1");
}

void game_server_config_security(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Setting strict client version verification");
	ini_value_set(cfg, GAME_SERVER_CFG_NET, "checkRuntime", "1");
}

void game_server_scripts_generate()
{
	PRINT_STATUS_NEW("Generating server's scripts");

	game_server_scripts_generate_gc();
	game_server_scripts_generate_main();
	
	PRINT_STATUS_DONE();
}

void game_server_scripts_generate_gc()
{
	PRINT_STATUS_NEW("Generating garbage collecting script");

	FILE* f_gc = fopen (GAME_SERVER_CMDF_GC_PATH, "w");  	
  	if (f_gc == NULL)
  	{
  		PRINT_STATUS_MSG_ERR("Unable to create file \"" GAME_SERVER_CMDF_GC "\"");
		PRINT_STATUS_FAIL();
  		exit(EXIT_FAILURE);
  	}
  	
  	fputs(GAME_SERVER_CMD_GC "\n", f_gc);
  	fputs(GAME_SERVER_CMD_GC "\n", f_gc);
  	fputs(GAME_SERVER_CMD_GC "\n", f_gc);
  	fputs(
  		GAME_SERVER_CMD_TIMEOUT(
  			"3600000",
  			GAME_SERVER_CMD_RUN_FILE(GAME_SERVER_CMDF_GC)) "\n", f_gc);
	fclose (f_gc);
	PRINT_STATUS_DONE();
}

void game_server_scripts_generate_main()
{
	PRINT_STATUS_NEW("Generating main script");

	FILE *f_sys = fopen (GAME_SERVER_CMDF_MAIN_PATH, "w");  	
  	if (f_sys == NULL)
  	{
  		PRINT_STATUS_MSG_ERR("Unable to create file \"" GAME_SERVER_CMDF_MAIN "\"");
		PRINT_STATUS_FAIL();
  		exit(EXIT_FAILURE);
  	}
  	
  	fputs (GAME_SERVER_CMD_RUN_FILE(GAME_SERVER_CMDF_GC) "\n", f_sys);
	fclose (f_sys);
	PRINT_STATUS_DONE();
}

#endif // GAME_SERVER_H