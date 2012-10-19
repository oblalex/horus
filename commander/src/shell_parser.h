#ifndef SHELL_PARSER_H
#define SHELL_PARSER_H

#include "util/common.h"

#define SH_EXIT 		"exit"

#define SH_CHAT_ALL 	"^chall[[:space:]]+([[:print:]]+)"
#define SH_CHAT_USER 	"^chusr[[:space:]]+([[:print:]]+)[[:space:]]+([[:print:]]+)"
#define SH_CHAT_ARMY 	"^charm[[:space:]]+(red|blue|none|r|b|n|1|2|0)[[:space:]]+([[:print:]]+)"

#define SH_MSSN_LOAD    "mload"
#define SH_MSSN_UNLOAD  "muload"
#define SH_MSSN_RUN     "mrun"
#define SH_MSSN_RERUN   "mrerun"
#define SH_MSSN_END     "mend"
#define SH_MSSN_START   "mstart"
#define SH_MSSN_RESTART "mrestart"
#define SH_MSSN_STOP    "mstop"
#define SH_MSSN_NEXT    "mnext"
#define SH_MSSN_PREV    "mprev"

void shell_parser_init();
void shell_parser_teardown();

void shell_parse_string(char* str);

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

#endif // SHELL_PARSER_H
