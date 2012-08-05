#include <wchar.h>
#include <stdlib.h>

#include "gs_scripts.h"
#include "gs_paths.h"
#include "gs_cmd.h"
#include "util/print_status.h"
#include "util/l10n.h"

void gs_scripts_generate()
{
    PRINT_STATUS_NEW(tr("Generating server's scripts"));

    gs_scripts_generate_gc();
    gs_scripts_generate_main();

    PRINT_STATUS_DONE();
}

void gs_scripts_generate_gc()
{
    PRINT_STATUS_NEW(tr("Generating garbage collecting script"));

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
    PRINT_STATUS_DONE();
}

void gs_scripts_generate_main()
{
    PRINT_STATUS_NEW(tr("Generating main script"));

    FILE* f_sys = fopen (PATH_GS_CMDF_MAIN, "w");
	check_script_file_created(f_sys, GS_CMDF_MAIN_NAME);

    fputs (GS_CMD_RUN_FILE(GS_CMDF_GC_NAME) "\n", f_sys);
    fclose (f_sys);

    PRINT_STATUS_DONE();
}

static void check_script_file_created(FILE* fd, char* fname)
{
    if (fd == NULL)
    {
		wchar_t msg[80];
		swprintf(&msg, L"%s \"%s\"", tr("Unable to create file"), fname);
        PRINT_STATUS_MSG_ERR(msg);
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }
}
