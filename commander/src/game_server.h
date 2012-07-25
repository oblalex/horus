#ifndef GAME_SERVER_H
#define GAME_SERVER_H

#include <stdlib.h>
#include <unistd.h>
#include "print_status.h"

#define GAME_SERVER_PATH 	PATH_PARENT PATH_SEP

#define GAME_SERVER_EXE 	"il2server.exe"
#define GAME_SERVER_EXE_PATH 	GAME_SERVER_PATH GAME_SERVER_EXE

void game_server_check_path();

void game_server_check_path()
{
	PRINT_STATUS_NEW("Checking IL-2 server's executable file path");

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

#endif // GAME_SERVER_H