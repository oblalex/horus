#ifndef SHELL_PARSER_H
#define SHELL_PARSER_H

#include "util/common.h"

#define SH_EXIT 		"exit"
#define SH_CHAT_ALL 	"^chall[[:space:]]+([[:print:]]+)"
#define SH_CHAT_USER 	"^chusr[[:space:]]+([[:print:]]+)[[:space:]]+([[:print:]]+)"
#define SH_CHAT_ARMY 	"^charm[[:space:]]+(red|blue|none|r|b|n|1|2|0)[[:space:]]+([[:print:]]+)"

void shell_parser_init();
void shell_parser_teardown();

void shell_parse_string(char* str);

static BOOL exit_match(char* str);
static BOOL chat_all_match(char* str);
static BOOL chat_user_match(char* str);
static BOOL chat_army_match(char* str);

#endif // SHELL_PARSER_H
