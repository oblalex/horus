#ifndef PRINT_STATUS_H
#define PRINT_STATUS_H

#include "globals.h"
#include "terminal.h"
#include "string_stack.h"

#define STATUS_MSG_HEAD "::"

#define STATUS_MSG_BUSY "BUSY"
#define STATUS_MSG_DONE "DONE"
#define STATUS_MSG_FAIL "FAIL"

STRING_STACK statuses;
char STATUS_LAST_OPER_WAS_PUSH = FALSE;

void print_status_tail(int color, char* msg)
{
	term_style(TA_BRIGHT, color, TC_NONE);
	printf(msg);
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("]");
	term_reset();
}
	
void print_status_raw(char* str, int color, char* msg)
{
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("%*s ", 2+statuses.count*3 , STATUS_MSG_HEAD);
	term_style(TA_BRIGHT, TC_NONE, TC_NONE);
	
	// 10 is for length of " :: " and length of status msg in brackets
	printf("%-*s", TERM_SIZE.ws_col-9-statuses.count*3, str);
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("[");
	print_status_tail(color, msg);	
}

void PRINT_STATUS_NEW(char* str)
{
	print_status_raw(str, TC_YELLOW, STATUS_MSG_BUSY);
	push(&statuses, str);
	STATUS_LAST_OPER_WAS_PUSH = TRUE;
}
	
void print_status_finished(int color, char* msg)
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
	
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
}

void PRINT_STATUSES_RESET()
{
	clear(&statuses);
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
}

#define PRINT_STATUS_DONE() print_status_finished(TC_GREEN, STATUS_MSG_DONE);
#define PRINT_STATUS_FAIL() print_status_finished(TC_RED,   STATUS_MSG_FAIL);

#endif // PRINT_STATUS_H