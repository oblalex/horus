#include <config.h>

#include "shell_parser.h"

#include <string.h>
#include <stdlib.h>

#include "gs.h"
#include "gs_cmd.h"
#include "gs_mission_manager.h"
#include "domain/d_army.h"
#include "util/print_status.h"
#include "util/str.h"
#include "util/l10n.h"
#include "util/regexxx.h"

static BOOL exit_match(char* str);

static BOOL chat_all_match(char* str);
static BOOL chat_user_match(char* str);
static BOOL chat_army_match(char* str);

static BOOL mssn_load_match(char* str);
static BOOL mssn_unload_match(char* str);
static BOOL mssn_run_match(char* str);
static BOOL mssn_rerun_match(char* str);
static BOOL mssn_end_match(char* str);
static BOOL mssn_start_match(char* str);
static BOOL mssn_restart_match(char* str);
static BOOL mssn_stop_match(char* str);
static BOOL mssn_next_match(char* str);
static BOOL mssn_prev_match(char* str);
static BOOL mssn_time_left_match(char* str);
static BOOL mssn_time_left_set_match(char* str);

static regex_t RE_chat_all;
static regex_t RE_chat_user;
static regex_t RE_chat_army;

static regex_t RE_mssn_time_left_set;

void shell_parser_init()
{
    PRINT_STATUS_NEW(tr("Shell parser initialization"));

	compile_regex(&RE_chat_all, SH_CHAT_ALL);
	compile_regex(&RE_chat_user, SH_CHAT_USER);
	compile_regex(&RE_chat_army, SH_CHAT_ARMY);
    compile_regex(&RE_mssn_time_left_set, SH_MSSN_TIME_LEFT_SET);

    PRINT_STATUS_DONE();
}

void shell_parser_teardown()
{
    PRINT_STATUS_NEW(tr("Shell parser tearing down"));

	regfree(&RE_chat_all);
	regfree(&RE_chat_user);
	regfree(&RE_chat_army);
    regfree(&RE_mssn_time_left_set);

    PRINT_STATUS_DONE();
}

void shell_parse_string(char* str)
{
	str_null_termitate(str);

    char* fstr;

#ifdef _WIN_
    char ustr[strlen(str)*6-5];
    str_escape_unicode(str, strlen(str), ustr, sizeof ustr);
    fstr = (char*)ustr;
#else
    fstr = str;
#endif

    if (chat_user_match(fstr)    == TRUE) return;
    if (chat_all_match(fstr)     == TRUE) return;
    if (chat_army_match(fstr)    == TRUE) return;


    if (mssn_load_match(fstr)    == TRUE) return;
    if (mssn_unload_match(fstr)  == TRUE) return;

    if (mssn_run_match(fstr)     == TRUE) return;
    if (mssn_rerun_match(fstr)   == TRUE) return;
    if (mssn_end_match(fstr)     == TRUE) return;

    if (mssn_start_match(fstr)   == TRUE) return;
    if (mssn_restart_match(fstr) == TRUE) return;
    if (mssn_stop_match(fstr)    == TRUE) return;

    if (mssn_next_match(fstr)    == TRUE) return;
    if (mssn_prev_match(fstr)    == TRUE) return;

    if (mssn_time_left_match(fstr)       == TRUE) return;
    if (mssn_time_left_set_match(fstr)   == TRUE) return;


    if (exit_match(fstr)         == TRUE) return;

	PRINT_STATUS_MSG_ERR(tr("Unknown command."));
}

BOOL exit_match(char* str)
{
	if (strcmp(str, SH_EXIT) != 0) return FALSE;
	gs_exit();
	return TRUE;
}

BOOL chat_all_match(char* str)
{
	const int n_matches = 2;
	regmatch_t m[n_matches];

	if (regexec(&RE_chat_all, str, n_matches, m, 0)) return FALSE;
	gs_cmd_chat_all(str+m[1].rm_so);
	return TRUE;
}

BOOL chat_user_match(char* str)
{
	const int n_matches = 3;
	regmatch_t m[n_matches];

	if (regexec(&RE_chat_user, str, n_matches, m, 0)) return FALSE;
	
    char callsign[m[1].rm_eo-m[1].rm_so+1];
    substring(m[1].rm_so, m[1].rm_eo, str, callsign, sizeof callsign);
    gs_cmd_chat_callsign(callsign, str+m[2].rm_so);
	return TRUE;
}

BOOL chat_army_match(char* str)
{
	const int n_matches = 3;
	regmatch_t m[n_matches];

	if (regexec(&RE_chat_army, str, n_matches, m, 0)) return FALSE;

	char army = *(str+m[1].rm_so);
	char* msg = str+m[2].rm_so;

	switch (army)
	{
		case '0': case 'n':
			gs_cmd_chat_army(ARMY_NONE, msg);
			break;
		case '1': case 'r':
			gs_cmd_chat_army(ARMY_RED, msg);
			break;
		case '2': case 'b':
			gs_cmd_chat_army(ARMY_BLUE, msg);
			break;
	}

	return TRUE;
}

BOOL mssn_load_match(char* str)
{
    if (strcmp(str, SH_MSSN_LOAD) != 0) return FALSE;
    gs_mssn_load_req();
    return TRUE;
}

BOOL mssn_unload_match(char* str)
{
    if (strcmp(str, SH_MSSN_UNLOAD) != 0) return FALSE;
    gs_mssn_unload_req();
    return TRUE;
}

BOOL mssn_run_match(char* str)
{
    if (strcmp(str, SH_MSSN_RUN) != 0) return FALSE;
    gs_mssn_run_req();
    return TRUE;
}

BOOL mssn_rerun_match(char* str)
{
    if (strcmp(str, SH_MSSN_RERUN) != 0) return FALSE;
    gs_mssn_rerun_req();
    return TRUE;
}

BOOL mssn_end_match(char* str)
{
    if (strcmp(str, SH_MSSN_END) != 0) return FALSE;
    gs_mssn_end_req();
    return TRUE;
}

BOOL mssn_start_match(char* str)
{
    if (strcmp(str, SH_MSSN_START) != 0) return FALSE;
    gs_mssn_start_req();
    return TRUE;
}

BOOL mssn_restart_match(char* str)
{
    if (strcmp(str, SH_MSSN_RESTART) != 0) return FALSE;
    gs_mssn_restart_req();
    return TRUE;
}

BOOL mssn_stop_match(char* str)
{
    if (strcmp(str, SH_MSSN_STOP) != 0) return FALSE;
    gs_mssn_stop_req();
    return TRUE;
}

static BOOL mssn_next_match(char* str)
{
    if (strcmp(str, SH_MSSN_NEXT) != 0) return FALSE;
    gs_mssn_next_req();
    return TRUE;
}

static BOOL mssn_prev_match(char* str)
{
    if (strcmp(str, SH_MSSN_PREV) != 0) return FALSE;
    gs_mssn_prev_req();
    return TRUE;
}

static BOOL mssn_time_left_match(char* str)
{
    if (strcmp(str, SH_MSSN_TIME_LEFT) != 0) return FALSE;
    gs_mssn_time_print_req();
    return TRUE;
}

static BOOL mssn_time_left_set_match(char* str)
{
    const int n_matches = 4;
    regmatch_t m[n_matches];

    if (regexec(&RE_mssn_time_left_set, str, n_matches, m, 0)) return FALSE;

    char h_str[m[1].rm_eo-m[1].rm_so+1];
    substring(m[1].rm_so, m[1].rm_eo, str, h_str, sizeof h_str);

    char m_str[m[2].rm_eo-m[2].rm_so+1];
    substring(m[2].rm_so, m[2].rm_eo, str, m_str, sizeof m_str);

    char s_str[m[3].rm_eo-m[3].rm_so+1];
    substring(m[3].rm_so, m[3].rm_eo, str, s_str, sizeof s_str);

    gs_mssn_time_left_set_req(atoi(h_str), atoi(m_str), atoi(s_str));
    return TRUE;
}
