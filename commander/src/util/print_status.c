#include <stdio.h>
#include <stdlib.h>
#include <wchar.h>
#include "str.h"
#include "print_status.h"

STACK_STR statuses;
BOOL STATUS_LAST_OPER_WAS_PUSH = FALSE;

void print_status_tail(int color, const char* msg)
{
	term_style(TA_BRIGHT, color, TC_NONE);
	wprintf(L"%s", msg);
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	wprintf(L"]");
	term_styleReset();
}
	
void print_status_raw(char* str, int color, const char* msg)
{
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	wprintf(L"%*s ", STATUS_HEAD_INDENT, STATUS_MSG_HEAD);
	term_style(TA_BRIGHT, TC_NONE, TC_NONE);
	
	// 5 is for length of ":: " + length of "[]"
	wprintf(L"%-*s", TERM_SIZE.ws_col-5-mbstowcs(NULL, msg, 0)-STATUS_HEAD_INDENT_PRIME, str);
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	wprintf(L"[");
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
		int i;
		for (i=0; i<mbstowcs(NULL, msg, 0); i++){
			wprintf(L"\b");	//erase STATUS_BUSY msg
		}
		print_status_tail(color, msg);		
	}	
	wprintf(L"\n");
	
	free(str);
	PRINT_STATUS_ORDER_RESET();
	fflush(stdout);
}

void print_status_msg(int color, const char* str, BOOL do_indent)
{
	if (STATUS_LAST_OPER_WAS_PUSH == TRUE)
	{
		wprintf(L"\n");
	}
  
	term_style(TA_NONE, color, TC_NONE);
	if (do_indent == TRUE)
	{
		wprintf(L"%*s%s\n", STATUS_MSG_INDENT, "", str);
	} else {
		wprintf(L"%s\n", str);
	}
	term_styleReset();
	
	PRINT_STATUS_ORDER_RESET();
	fflush(stdout);
}

void PRINT_STATUSES_RESET()
{
	while(statuses.size>0)
	{
		free(pop(&statuses));
	}
}

void PRINT_STATUS_ORDER_RESET(){
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
}
