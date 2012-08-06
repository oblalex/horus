#include "shell_parser.h"

#include <string.h>
#include <regex.h>

#include "gs.h"
#include "gs_cmd.h"
#include "util/print_status.h"
#include "util/str.h"
#include "util/regexxx.h"

static regex_t RE_chat_all;

void shell_parser_init()
{
	compile_regex(&RE_chat_all, SH_CHAT_ALL);
}

void shell_parser_teardown()
{
	regfree(&RE_chat_all);
}

void shell_parse_string(char* str)
{	
	str_null_termitate(str);
	if (exit_match(str) == TRUE) return;
	if (chat_all_match(str) == TRUE) return;
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
}
