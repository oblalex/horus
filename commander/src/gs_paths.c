#include <config.h>

#include <errno.h>
#include <stdlib.h>
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

		int len = 80;
		wchar_t msg[len];
		swprintf(msg, len, L"%s \"" GS_EXE_NAME "\".", tr("3. IL-2 server's executable file is named as"));

        PRINT_STATUS_MSG_ERR((char*)&msg);
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }
}

void gs_check_path_logs()
{
    PRINT_STATUS_NEW(tr("Checking logs path"));

    struct stat st;
    char* path = PATH_GS_LOGS_DIR;

    if ((stat(path, &st) != 0) && (errno == ENOENT))
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
			char* fail_msg = tr("Failed to create"); 
			int len = strlen(path)+mbstowcs(NULL, fail_msg, 0)+3+1;
            wchar_t err_msg[len];           
			swprintf(err_msg, len, L"%s \"%s\"", fail_msg, path);

            PRINT_STATUS_MSG_ERR((char*)&err_msg);
            PRINT_STATUS_FAIL();
            exit(EXIT_FAILURE);
        }
    }

    PRINT_STATUS_DONE();
}
