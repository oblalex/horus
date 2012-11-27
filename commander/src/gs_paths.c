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
    BOOL shLock = FALSE;

    PRINT_STATUS_NEW(tr("Checking root path"), shLock);
	const char* const path = PATH_GS_EXE;
    if (access(path, X_OK) != -1) {
        PRINT_STATUS_DONE(shLock);
    } else {
        PRINT_STATUS_MSG_ERR(tr("Please, make sure in following:"), shLock);
        PRINT_STATUS_MSG_ERR(tr("1. This program is located in IL-2 server's subdirectory."), shLock);
        PRINT_STATUS_MSG_ERR(tr("2. You are located at that directory currently."), shLock);

        char msg[80];
        sprintf(msg, "%s \"%s\".", tr("3. IL-2 server's executable file is named as"), GS_EXE_NAME);

        PRINT_STATUS_MSG_ERR((char*)&msg, shLock);
        PRINT_STATUS_FAIL(shLock);
        exit(EXIT_FAILURE);
    }
}

void gs_check_path_logs()
{
    BOOL shLock = FALSE;

    PRINT_STATUS_NEW(tr("Checking logs path"), shLock);

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
        PRINT_STATUS_MSG(tr("Creating missing directory"), shLock);
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
			
            PRINT_STATUS_MSG_ERR((char*)&err_msg, shLock);
            PRINT_STATUS_FAIL(shLock);
            exit(EXIT_FAILURE);
        }
    }

    PRINT_STATUS_DONE(shLock);
}

BOOL gs_check_path_mission_list()
{
    BOOL shLock = FALSE;

    PRINT_STATUS_NEW(tr("Checking missions list path"), shLock);
    const char* const path = PATH_GS_MISSION_LIST;
    if (access(path, X_OK) != -1) {
        PRINT_STATUS_DONE(shLock);
        return TRUE;
    } else {
        PRINT_STATUS_MSG_ERR(tr("Missions list was not found. Please, read the manual about missions managing."), shLock);
        PRINT_STATUS_FAIL(shLock);
        return FALSE;
    }
}

BOOL gs_check_path_mission(char* name, char* path)
{
    BOOL shLock = FALSE;
    char fullPath[255];
    sprintf(fullPath, "%s%s", PATH_GS_MISSIONS_DIR, path);
    if (access(fullPath, X_OK) != -1) {
        return TRUE;
    } else {
        PRINT_STATUS_MSG_ERR(tr("Mission's path is wrong:"), shLock);

        char msg[255];
        sprintf(msg, "\"%s\" : \"%s\"", name, path);
        PRINT_STATUS_MSG_ERR(msg, shLock);
        return FALSE;
    }
}
