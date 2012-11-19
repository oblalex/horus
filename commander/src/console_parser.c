#include "console_parser.h"
#include "util/str.h"
#include "util/print_status.h"
#include <string.h>
#include "gs_mission_manager.h"

static BOOL mission_match(char* str);

void console_parse_string(char* str)
{
    str_null_termitate(str);
    if (mission_match(str) == TRUE) return;
    PRINT_STATUS_MSG(str);
}

BOOL mission_match(char* str)
{
    if (strstr(str, "Mission") == NULL) return FALSE;

    if (strstr(str, "is Loaded") != NULL)
    {
        gs_mssn_manager_notify_loaded();
    } else if (strstr(str, "NOT loaded") != NULL)
    {
        gs_mssn_manager_notify_not_loaded();
    } else if (strstr(str, "is Playing") != NULL)
    {
        gs_mssn_manager_notify_running();
    }

    return TRUE;
}
