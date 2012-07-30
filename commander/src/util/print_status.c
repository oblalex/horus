#include <stdio.h>

#include "print_status.h"
#include "str.h"

STACK_STR statuses;
BOOL STATUS_LAST_OPER_WAS_PUSH = FALSE;

void print_status_tail(int color, const char* msg)
{
	term_style(TA_BRIGHT, color, TC_NONE);
	printf(msg);
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("]");
	term_styleReset();
}
	
void print_status_raw(char* str, int color, const char* msg)
{
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("%*s ", STATUS_HEAD_INDENT, STATUS_MSG_HEAD);
	term_style(TA_BRIGHT, TC_NONE, TC_NONE);
	
	// 9 is for length of ":: " and length of status msg in brackets
	printf("%-*s", TERM_SIZE.ws_col-9-STATUS_HEAD_INDENT_PRIME, str);
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("[");
	print_status_tail(color, msg);	
}

void PRINT_STATUS_NEW(char* str)
{
	char* str_new = str_copy(str);
	print_status_raw(str_new, TC_YELLOW, STATUS_MSG_BUSY);
	
	push(&statuses, str_new);
	STATUS_LAST_OPER_WAS_PUSH = TRUE;
	fflush(stdout);
}
	
void print_status_finished(int color, const char* msg)
{
	char* str = pop(&statuses);
	
	if (STATUS_LAST_OPER_WAS_PUSH == FALSE)
	{
		print_status_raw(str, color, msg);		
	} else {
		printf("\b\b\b\b"); //erase "BUSY" msg
		print_status_tail(color, msg);		
	}	
	printf("\n");
	
	free(str);
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
	fflush(stdout);
}

void print_status_msg(int color, const char* str, BOOL do_indent)
{
	if (STATUS_LAST_OPER_WAS_PUSH == TRUE)
	{
		printf("\n");
	}
  
	term_style(TA_NONE, color, TC_NONE);
	if (do_indent == TRUE)
	{
		printf("%*s%s\n", STATUS_MSG_INDENT, "", str);
	} else {
		printf("%s\n", str);
	}
	term_styleReset();
	
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
	fflush(stdout);
}

void PRINT_STATUSES_RESET()
{
	while(statuses.size>0)
	{
		free(pop(&statuses));
	}
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
}