#include <config.h>
#include <wchar.h>
#include <stdlib.h>

#include "gs_scripts.h"
#include "gs_paths.h"
#include "gs_cmd.h"
#include "util/print_status.h"
#include "util/l10n.h"

static void check_script_file_created(FILE* fd, char* fname);

void gs_scripts_generate()
{
    BOOL shLock = FALSE;

    PRINT_STATUS_NEW(tr("Generating server's scripts"), shLock);

    gs_scripts_generate_gc();
    gs_scripts_generate_main();

    PRINT_STATUS_DONE(shLock);
}

void gs_scripts_generate_gc()
{
    BOOL shLock = FALSE;

    PRINT_STATUS_NEW(tr("Generating garbage collecting script"), shLock);

    FILE* f_gc = fopen (PATH_GS_CMDF_GC, "w");
	check_script_file_created(f_gc, GS_CMDF_GC_NAME);

    fputs(GS_CMD_GC "\n", f_gc);
    fputs(GS_CMD_GC "\n", f_gc);
    fputs(GS_CMD_GC "\n", f_gc);
    fputs(
        GS_CMD_TIMEOUT(
            "3600000",
            GS_CMD_RUN_FILE(GS_CMDF_GC_NAME)) "\n", f_gc);
    fclose (f_gc);
    PRINT_STATUS_DONE(shLock);
}

void gs_scripts_generate_main()
{
    BOOL shLock = FALSE;

    PRINT_STATUS_NEW(tr("Generating main script"), shLock);

    FILE* f_sys = fopen (PATH_GS_CMDF_MAIN, "w");
	check_script_file_created(f_sys, GS_CMDF_MAIN_NAME);

    fputs (GS_CMD_RUN_FILE(GS_CMDF_GC_NAME) "\n", f_sys);
    fclose (f_sys);

    PRINT_STATUS_DONE(shLock);
}

static void check_script_file_created(FILE* fd, char* fname)
{
    if (fd == NULL)
    {
        BOOL shLock = FALSE;
		int len = 80;
		wchar_t msg[len];

#ifdef _WIN_
        swprintf(msg, L"%S \"%S\"", tr("Unable to create file"), fname);
#else
		swprintf(msg, len, L"%s \"%s\"", tr("Unable to create file"), fname);
#endif

        PRINT_STATUS_MSG_ERR((char*)&msg, shLock);
        PRINT_STATUS_FAIL(shLock);
        exit(EXIT_FAILURE);
    }
}
