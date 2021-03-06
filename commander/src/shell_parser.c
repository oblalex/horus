#include <config.h>

#include "shell_parser.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "gs.h"
#include "gs_cmd.h"
#include "gs_paths.h"
#include "gs_mission_manager.h"
#include "pilot_manager.h"
#include "domain/d_army.h"
#include "util/print_status.h"
#include "util/str.h"
#include "util/l10n.h"
#include "util/regexxx.h"
#include "util/linenoise.h"

static void completion(const char *buf, linenoiseCompletions *lc);

static void regexInit();
static void regexFree();

static void extensionsInit();
static void cmdRawAdd(char* cmd, int pos);
static void extensionsFree();

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

static SH_CMD_RAW CMDS_RAW[SH_CMD_COUNT] = {{ 0 }};

void shell_parser_init()
{
    BOOL shLock = FALSE;
    PRINT_STATUS_NEW(tr("Shell parser initialization"), shLock);

    regexInit();
    extensionsInit();

    PRINT_STATUS_DONE(shLock);
}


void regexInit()
{
    compile_regex(&RE_chat_all, SH_CHAT_ALL);
    compile_regex(&RE_chat_user, SH_CHAT_USER);
    compile_regex(&RE_chat_army, SH_CHAT_ARMY);
    compile_regex(&RE_mssn_time_left_set, SH_MSSN_TIME_LEFT_SET);
}

void extensionsInit()
{
    linenoiseSetCompletionCallback(completion);
    linenoiseHistoryLoad(SH_HISTORY_NAME);

    int i = 0;
    cmdRawAdd(SH_EXIT,          i++);

    cmdRawAdd(SH_CHAT_ALL_RAW,  i++);
    cmdRawAdd(SH_CHAT_USER_RAW, i++);
    cmdRawAdd(SH_CHAT_ARMY_RAW, i++);

    cmdRawAdd(SH_MSSN_LOAD,     i++);
    cmdRawAdd(SH_MSSN_UNLOAD,   i++);
    cmdRawAdd(SH_MSSN_END,      i++);
    cmdRawAdd(SH_MSSN_RUN,      i++);
    cmdRawAdd(SH_MSSN_RERUN,    i++);
    cmdRawAdd(SH_MSSN_START,    i++);
    cmdRawAdd(SH_MSSN_STOP,     i++);
    cmdRawAdd(SH_MSSN_RESTART,  i++);
    cmdRawAdd(SH_MSSN_NEXT,     i++);
    cmdRawAdd(SH_MSSN_PREV,     i++);

    cmdRawAdd(SH_MSSN_TIME_LEFT, i++);
    cmdRawAdd(SH_MSSN_TIME_LEFT " set", i++);
}

void cmdRawAdd(char* cmd, int pos)
{
    CMDS_RAW[pos].ln = strlen(cmd);
    CMDS_RAW[pos].cmd = (char*)malloc(sizeof(char)*CMDS_RAW[pos].ln);
    memcpy(CMDS_RAW[pos].cmd, cmd, CMDS_RAW[pos].ln+1);
}

void shell_parser_teardown()
{
    BOOL shLock = TRUE;
    PRINT_STATUS_NEW(tr("Shell parser tearing down"), shLock);

    regexFree();
    extensionsFree();

    PRINT_STATUS_DONE(shLock);
}

void regexFree()
{
    regfree(&RE_chat_all);
    regfree(&RE_chat_user);
    regfree(&RE_chat_army);
    regfree(&RE_mssn_time_left_set);
}

void extensionsFree()
{
    int i;
    for(i=0; i<SH_CMD_COUNT; ++i)
        free(CMDS_RAW[i].cmd);
}

void completion(const char *buf, linenoiseCompletions *lc)
{
    int i, j, ln, used;
    ln = strlen(buf);

    if (ln == 0) return;

    for(i=0; i<SH_CMD_COUNT; ++i)
    {
        used = 1;


        for (j = 0; (j<ln) && (j<CMDS_RAW[i].ln); j++)
            if ((buf[j] != CMDS_RAW[i].cmd[j]) && (buf[j] != '\0') && (CMDS_RAW[i].cmd[j] != '\0'))
            {
                used = 0;
                break;
            }

        if (used)
        {
            linenoiseAddCompletion(lc, CMDS_RAW[i].cmd);
            if (strcmp(CMDS_RAW[i].cmd, SH_CHAT_ARMY_RAW)==0)
            {
                linenoiseAddCompletion(lc, SH_CHAT_ARMY_RAW " " SH_CHAT_ARMY_R);
                linenoiseAddCompletion(lc, SH_CHAT_ARMY_RAW " " SH_CHAT_ARMY_B);
                linenoiseAddCompletion(lc, SH_CHAT_ARMY_RAW " " SH_CHAT_ARMY_N);
            } else if (strcmp(CMDS_RAW[i].cmd, SH_CHAT_USER_RAW)==0)
            {
                char** callsignes = NULL;
                uint2 cnt = pm_pilots_list(&callsignes);
                int i;

                char cmd[50];

                for(i=0; i<cnt; ++i)
                {
                    strcpy(cmd, SH_CHAT_USER_RAW " ");
                    strcat(cmd, callsignes[i]);

                    linenoiseAddCompletion(lc, cmd);
                    free(callsignes[i]);
                }

                if (callsignes!=NULL) free (callsignes);
            }
        }
    }
}

void shell_handle_in(BOOL (*run_condition)())
{
    char* line;

    while((*run_condition)() == TRUE)
    {
        line = linenoise(SH_PROMPT);

        if(line==NULL)
        {
            gs_exit();
            break;
        }

        if (line[0] != '\0')
        {
            shell_parse_string(line);
            linenoiseHistoryAdd(line);
            linenoiseHistorySave(SH_HISTORY_NAME);
        } else {
            usleep(20*1000);
        }

        free(line);
    }
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

    PRINT_STATUS_MSG_ERR(tr("Unknown command."), FALSE);
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

void shell_lock()
{
    linenoiseLock();
}

void shell_unlock()
{
    linenoiseUnlock();
}
