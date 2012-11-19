#include "console_parser.h"
#include "util/str.h"
#include "util/print_status.h"
#include "util/regexxx.h"
#include "gs_mission_manager.h"
#include <string.h>

static BOOL mission_match(char* str);

static regex_t RE_user_join;
static regex_t RE_user_left;

void console_parser_init()
{
    PRINT_STATUS_NEW(tr("Console parser initialization"));

    compile_regex(&RE_user_join, CNSL_USER_JOIN);
    compile_regex(&RE_user_left, CNSL_USER_LEFT);

    PRINT_STATUS_DONE();
}

void console_parser_teardown()
{
    PRINT_STATUS_NEW(tr("Console parser tearing down"));

    regfree(&RE_user_join);
    regfree(&RE_user_left);

    PRINT_STATUS_DONE();
}

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
