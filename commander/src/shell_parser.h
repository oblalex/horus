#ifndef SHELL_PARSER_H
#define SHELL_PARSER_H

#include "util/common.h"

#define SH_EXIT "exit"

void shell_parse_string(char* str);

static BOOL exit_match(char* str);

#endif // SHELL_PARSER_H
