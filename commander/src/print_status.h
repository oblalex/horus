#ifndef PRINT_STATUS_H
#define PRINT_STATUS_H

#include "utils.h"
#include "globals.h"
#include "terminal.h"
#include "string_stack.h"

#define STATUS_MSG_HEAD "::"

#define STATUS_MSG_BUSY "BUSY"
#define STATUS_MSG_DONE "DONE"
#define STATUS_MSG_FAIL "FAIL"

STRING_STACK statuses;
BOOL STATUS_LAST_OPER_WAS_PUSH = FALSE;

#define STATUS_INDENT(SUB) 2+(statuses.count-SUB)*3
#define STATUS_HEAD_INDENT STATUS_INDENT(0)
#define STATUS_MSG_INDENT STATUS_INDENT(1)+1

void print_status_tail(int color, const char* msg)
{
	term_style(TA_BRIGHT, color, TC_NONE);
	printf(msg);
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("]");
	term_style_reset();
}
	
void print_status_raw(char* str, int color, const char* msg)
{
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("%*s ", STATUS_HEAD_INDENT, STATUS_MSG_HEAD);
	term_style(TA_BRIGHT, TC_NONE, TC_NONE);
	
	// 10 is for length of " :: " and length of status msg in brackets
	printf("%-*s", TERM_SIZE.ws_col-9-statuses.count*3, str);
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("[");
	print_status_tail(color, msg);	
}

void PRINT_STATUS_NEW(char* str)
{
	char* str_new = strCopy(str);
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

#define PRINT_STATUS_DONE() print_status_finished(TC_GREEN, STATUS_MSG_DONE);
#define PRINT_STATUS_FAIL() print_status_finished(TC_RED,   STATUS_MSG_FAIL);

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
	term_style_reset();
	
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
	fflush(stdout);
}

#define PRINT_STATUS_MSG(STR)     print_status_msg(TC_CYAN, STR, TRUE);
#define PRINT_STATUS_MSG_ERR(STR) print_status_msg(TC_RED,  STR, TRUE);

#define PRINT_STATUS_MSG_NOIND(STR)     print_status_msg(TC_CYAN, STR, FALSE);
#define PRINT_STATUS_MSG_ERR_NOIND(STR) print_status_msg(TC_RED,  STR, FALSE);

void PRINT_STATUSES_RESET()
{
	while(statuses.count>0)
	{
		free(pop(&statuses));
	}
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
}

#endif // PRINT_STATUS_H