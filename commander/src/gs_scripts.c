#include <stdio.h>
#include <stdlib.h>

#include "gs_scripts.h"
#include "gs_paths.h"
#include "gs_cmd.h"
#include "util/print_status.h"

void gs_scripts_generate()
{
    PRINT_STATUS_NEW("Generating server's scripts");

    gs_scripts_generate_gc();
    gs_scripts_generate_main();

    PRINT_STATUS_DONE();
}

void gs_scripts_generate_gc()
{
    PRINT_STATUS_NEW("Generating garbage collecting script");

    FILE* f_gc = fopen (PATH_GS_CMDF_GC, "w");
    if (f_gc == NULL)
    {
        PRINT_STATUS_MSG_ERR("Unable to create file \"" GS_CMDF_GC_NAME "\"");
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }

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
    PRINT_STATUS_NEW("Generating main script");

    FILE* f_sys = fopen (PATH_GS_CMDF_MAIN, "w");
    if (f_sys == NULL)
    {
        PRINT_STATUS_MSG_ERR("Unable to create file \"" GS_CMDF_MAIN_NAME "\"");
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }

    fputs (GS_CMD_RUN_FILE(GS_CMDF_GC_NAME) "\n", f_sys);
    fclose (f_sys);

    PRINT_STATUS_DONE();
}
