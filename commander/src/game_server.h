#ifndef GAME_SERVER_H
#define GAME_SERVER_H

#include <stdlib.h>
#include <unistd.h>
#include "print_status.h"
#include "ini_helper.h"

#define GAME_SERVER_PATH 	PATH_PARENT PATH_SEP

#define GAME_SERVER_EXE 	"il2server.exe"
#define GAME_SERVER_EXE_PATH 	GAME_SERVER_PATH GAME_SERVER_EXE

#define GAME_SERVER_CFG 	"confs.ini"
#define GAME_SERVER_CFG_PATH 	GAME_SERVER_PATH GAME_SERVER_CFG

#define GAME_SERVER_CFG_CHAT	"chat"
#define GAME_SERVER_CFG_GAME	"game"
#define GAME_SERVER_CFG_CONSOLE	"Console"
#define GAME_SERVER_CFG_NET	"NET"

#define GAME_SERVER_LOG		"eventlog.lst"

void game_server_init();
void game_server_check_path();
void game_server_check_settings();

void game_server_config_logging(INI_CONTAINER* cfg);
void game_server_config_logging_chat(INI_CONTAINER* cfg);
void game_server_config_logging_console(INI_CONTAINER* cfg);
void game_server_config_logging_file(INI_CONTAINER* cfg);
void game_server_config_security(INI_CONTAINER* cfg);

void game_server_init(){
	game_server_check_path();
	game_server_check_settings();
}

void game_server_check_path()
{
	PRINT_STATUS_NEW("Checking server's executable file path");

	if( access( GAME_SERVER_EXE_PATH, X_OK ) != -1 ) {
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
	
	BOOL failed = FALSE;
	
	INI_CONTAINER* cfg;
	cfg = ini_start(GAME_SERVER_CFG_PATH);
	
	if (cfg == NULL) {
		failed = TRUE;
		PRINT_STATUS_MSG_ERR(INI_MALLOC_ERR_CONTAINER);
	} else {
		if ((failed = ini_has_err(cfg)) == TRUE) {
			PRINT_STATUS_MSG_ERR(cfg->error_msg);
		}
		
		game_server_config_logging(cfg);
		game_server_config_security(cfg);
		
		ini_end(cfg);
		
		if ((failed = ini_has_err(cfg)) == TRUE) {
			PRINT_STATUS_MSG_ERR(cfg->error_msg);
		}
	}

	if (failed == TRUE) {
		PRINT_STATUS_FAIL();
		exit(EXIT_FAILURE);
	} else {
		PRINT_STATUS_DONE();
	}
}

void game_server_config_logging(INI_CONTAINER* cfg)
{
	game_server_config_logging_chat(cfg);
	game_server_config_logging_console(cfg);
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
	ini_value_set(cfg, GAME_SERVER_CFG_CONSOLE, "LOG", "0");
}

void game_server_config_logging_file(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Setting logging output file");
	ini_value_set(cfg, GAME_SERVER_CFG_GAME, "eventlog", GAME_SERVER_LOG);
	
	PRINT_STATUS_MSG("Enabling log resetting for every mission");
	ini_value_set(cfg, GAME_SERVER_CFG_GAME, "eventlogkeep", "0");
	
	PRINT_STATUS_MSG("Enabling buildings destruction logging");
	ini_value_set(cfg, GAME_SERVER_CFG_GAME, "eventlogHouse", "1");
}

void game_server_config_security(INI_CONTAINER* cfg)
{
	PRINT_STATUS_MSG("Setting strict client version verification");
	ini_value_set(cfg, GAME_SERVER_CFG_NET, "checkRuntime", "2");
}

#endif // GAME_SERVER_H