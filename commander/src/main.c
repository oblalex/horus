#include <unistd.h>
#include "print_status.h"

#define SERVER_EXE "../il2server.exe"

void checkDir();

int main (int argc, char const *argv[])
{
	term_init();
	checkDir();	
	return 0;	
}

void checkDir()
{
	PRINT_STATUS_NEW("Looking for IL-2 server executable");
	
	if( access( SERVER_EXE, X_OK ) != -1 ) {
		PRINT_STATUS_DONE
	} else {
		PRINT_STATUS_FAIL
	}
}
