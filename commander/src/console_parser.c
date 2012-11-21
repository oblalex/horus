#include "console_parser.h"
#include "util/str.h"
#include "util/print_status.h"
#include "util/regexxx.h"
#include "gs_mission_manager.h"
#include "pilot_manager.h"

#include <string.h>
#include <stdlib.h>

static BOOL mission_match(char* str);

static BOOL user_join_match(char* str);
static BOOL user_left_match(char* str);
static BOOL mtl_match(char* str);

static regex_t RE_user_join;
static regex_t RE_user_left;
static regex_t RE_mtl;

void console_parser_init()
{
    PRINT_STATUS_NEW(tr("Console parser initialization"));

    compile_regex(&RE_user_join,    CNSL_USER_JOIN);
    compile_regex(&RE_user_left,    CNSL_USER_LEFT);
    compile_regex(&RE_mtl,          CNSL_CMD_MTL);

    PRINT_STATUS_DONE();
}

void console_parser_teardown()
{
    PRINT_STATUS_NEW(tr("Console parser tearing down"));

    regfree(&RE_user_join);
    regfree(&RE_user_left);
    regfree(&RE_mtl);

    PRINT_STATUS_DONE();
}

void console_parse_string(char* str)
{
    str_null_termitate(str);

    if (mission_match(str)      == TRUE) return;
    if (user_join_match(str)    == TRUE) return;
    if (user_left_match(str)    == TRUE) return;
    if (mtl_match(str)          == TRUE) return;

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

static BOOL user_join_match(char* str)
{
    const int n_matches = 4;
    regmatch_t m[n_matches];

    if (regexec(&RE_user_join, str, n_matches, m, 0)) return FALSE;

    char channel[m[1].rm_eo-m[1].rm_so+1];
    substring(m[1].rm_so, m[1].rm_eo, str, channel, sizeof channel);

    char ip[m[2].rm_eo-m[2].rm_so+1];
    substring(m[2].rm_so, m[2].rm_eo, str, ip, sizeof ip);

    char callsign[m[3].rm_eo-m[3].rm_so+1];
    substring(m[3].rm_so, m[3].rm_eo, str, callsign, sizeof callsign);

    pm_user_join_req(callsign, ip, atoi(channel));

    return TRUE;
}

static BOOL user_left_match(char* str)
{
    const int n_matches = 2;
    regmatch_t m[n_matches];

    if (regexec(&RE_user_left, str, n_matches, m, 0)) return FALSE;

    char channel[m[1].rm_eo-m[1].rm_so+1];
    substring(m[1].rm_so, m[1].rm_eo, str, channel, sizeof channel);

    pm_user_left_req(atoi(channel));

    return TRUE;
}

static BOOL mtl_match(char* str)
{
    const int n_matches = 2;
    regmatch_t m[n_matches];

    if (regexec(&RE_mtl, str, n_matches, m, 0)) return FALSE;

    char callsign[m[1].rm_eo-m[1].rm_so+1];
    substring(m[1].rm_so, m[1].rm_eo, str, callsign, sizeof callsign);

    pm_mtl_req(callsign);

    return TRUE;
}
