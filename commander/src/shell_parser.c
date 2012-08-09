#include "shell_parser.h"

#include <string.h>
#include <regex.h>

#include "gs.h"
#include "gs_cmd.h"
#include "gs_armies.h"
#include "util/print_status.h"
#include "util/str.h"
#include "util/l10n.h"
#include "util/regexxx.h"

static regex_t RE_chat_all;
static regex_t RE_chat_user;
static regex_t RE_chat_army;

void shell_parser_init()
{
	compile_regex(&RE_chat_all, SH_CHAT_ALL);
	compile_regex(&RE_chat_user, SH_CHAT_USER);
	compile_regex(&RE_chat_army, SH_CHAT_ARMY);
}

void shell_parser_teardown()
{
	regfree(&RE_chat_all);
	regfree(&RE_chat_user);
	regfree(&RE_chat_army);
}

void shell_parse_string(char* str)
{	
	str_null_termitate(str);
	if (chat_user_match(str) == TRUE) return;
	if (chat_all_match(str) == TRUE) return;
	if (chat_army_match(str) == TRUE) return;
	if (exit_match(str) == TRUE) return;
	PRINT_STATUS_MSG_ERR(tr("Unknown command."));
}

static BOOL exit_match(char* str)
{
	if (strcmp(str, SH_EXIT) != 0) return FALSE;
	gs_exit();
	return TRUE;
}

static BOOL chat_all_match(char* str)
{
	const int n_matches = 2;
	regmatch_t m[n_matches];

	if (regexec(&RE_chat_all, str, n_matches, m, 0)) return FALSE;
	gs_cmd_chat_all(str+m[1].rm_so);
	return TRUE;
}

static BOOL chat_user_match(char* str)
{
	const int n_matches = 3;
	regmatch_t m[n_matches];

	if (regexec(&RE_chat_user, str, n_matches, m, 0)) return FALSE;
	
	char username[m[1].rm_eo-m[1].rm_so+1];
	substring(m[1].rm_so, m[1].rm_eo, str, username, sizeof username);
	gs_cmd_chat_username(username, str+m[2].rm_so);
	return TRUE;
}

static BOOL chat_army_match(char* str)
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
