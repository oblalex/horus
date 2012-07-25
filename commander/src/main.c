#include <unistd.h>
#include "print_status.h"

#define SERVER_EXE "il2server.exe"
#define SERVER_EXE_PATH "../" SERVER_EXE

void checkDir();

int main (int argc, char const *argv[])
{
	term_init();
	checkDir();
	return 0;
}

void checkDir()
{
	PRINT_STATUS_NEW("Looking for IL-2 server's executable file");
	
	if( access( SERVER_EXE_PATH, X_OK ) != -1 ) {
		PRINT_STATUS_DONE();
	} else {
		PRINT_STATUS_MSG_ERR("Please, make sure in following:");
		PRINT_STATUS_MSG_ERR("1. This program is located in IL-2 server's subdirectory.");
		PRINT_STATUS_MSG_ERR("2. That directory is set as current.");
		PRINT_STATUS_MSG_ERR("3. IL-2 server's executable file is named as \"" SERVER_EXE "\".");
		PRINT_STATUS_FAIL();
	}
}
