#include <config.h>

#include <errno.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <wchar.h>

#include "gs_paths.h"
#include "util/print_status.h"
#include "util/l10n.h"

#ifdef _WIN_
	#include <dir.h>
#else
	#include <unistd.h>
#endif

void gs_check_path_root()
{
    PRINT_STATUS_NEW(tr("Checking root path"));
	const char* const path = PATH_GS_EXE;
    if (access(path, X_OK) != -1) {
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_MSG_ERR(tr("Please, make sure in following:"));
        PRINT_STATUS_MSG_ERR(tr("1. This program is located in IL-2 server's subdirectory."));
        PRINT_STATUS_MSG_ERR(tr("2. You are located at that directory currently."));

        char msg[80];
        sprintf(msg, "%s \"%s\".", tr("3. IL-2 server's executable file is named as"), GS_EXE_NAME);

        PRINT_STATUS_MSG_ERR((char*)&msg);
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }
}

void gs_check_path_logs()
{
    PRINT_STATUS_NEW(tr("Checking logs path"));

    char* path = PATH_GS_LOGS_DIR;
	
#ifdef _WIN_
	DWORD dwAttrib = GetFileAttributes(path);
	if (!(dwAttrib != INVALID_FILE_ATTRIBUTES && 
         (dwAttrib & FILE_ATTRIBUTE_DIRECTORY)))
#else
	struct stat st;
	if ((stat(path, &st) != 0) && (errno == ENOENT))
#endif	
    {
        PRINT_STATUS_MSG(tr("Creating missing directory"));
		int mk_result;
		
	#ifdef _WIN_
		mk_result = mkdir(path);
	#else
		mk_result = mkdir(path, S_IRWXU);
	#endif
		
        if (mk_result != 0)
        {
			char err_msg[80];
			sprintf(err_msg, "%s \"%s\".", tr("Failed to create"), path);
			
            PRINT_STATUS_MSG_ERR((char*)&err_msg);
            PRINT_STATUS_FAIL();
            exit(EXIT_FAILURE);
        }
    }

    PRINT_STATUS_DONE();
}

BOOL gs_check_path_mission_list()
{
    PRINT_STATUS_NEW(tr("Checking missions list path"));
    const char* const path = PATH_GS_MISSION_LIST;
    if (access(path, X_OK) != -1) {
        PRINT_STATUS_DONE();
        return TRUE;
    } else {
        PRINT_STATUS_MSG_ERR(tr("Missions list was not found. Please, read the manual about missions managing."));
        PRINT_STATUS_FAIL();
        return FALSE;
    }
}

BOOL gs_check_path_mission(char* name, char* path)
{
    char fullPath[255];
    sprintf(fullPath, "%s%s", PATH_GS_MISSIONS_DIR, path);
    if (access(fullPath, X_OK) != -1) {
        return TRUE;
    } else {
        PRINT_STATUS_MSG_ERR(tr("Mission's path is wrong:"));

        char msg[255];
        sprintf(msg, "\"%s\" : \"%s\"", name, path);
        PRINT_STATUS_MSG_ERR(msg);
        return FALSE;
    }
}
