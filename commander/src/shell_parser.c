#include "shell_parser.h"

#include <string.h>

#include "gs.h"
#include "util/print_status.h"
#include "util/str.h"

void shell_parse_string(char* str)
{	
	str_null_termitate(str);
	if (exit_match(str) == TRUE) return;
}

static BOOL exit_match(char* str)
{
	if (strcmp(str, SH_EXIT) != 0) return FALSE;

	gs_exit();

	return TRUE;
}
