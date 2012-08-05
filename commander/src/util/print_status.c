#include <stdio.h>
#include <stdlib.h>
#include <wchar.h>
#include <pthread.h>

#include "str.h"
#include "print_status.h"

static STACK_STR statuses;
static BOOL STATUS_LAST_OPER_WAS_PUSH = FALSE;

static BOOL MULTIMODE = FALSE;
static pthread_mutex_t LOCK;

#define MULTILOCK() if (MULTIMODE==TRUE) pthread_mutex_lock(&LOCK);
#define MULTIUNLOCK() if (MULTIMODE==TRUE) pthread_mutex_unlock(&LOCK);

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
	MULTILOCK();

	char* str_new = str_copy(str);	
	print_status_raw(str_new, TC_YELLOW, STATUS_MSG_BUSY);
	
	push(&statuses, str_new);
	STATUS_LAST_OPER_WAS_PUSH = TRUE;
	fflush(stdout);

	MULTIUNLOCK();
}
	
void print_status_finished(int color, const char* msg)
{
	MULTILOCK();

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
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
	fflush(stdout);

	MULTIUNLOCK();
}

void print_status_msg(int color, const char* str, BOOL do_indent)
{
	MULTILOCK();

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
	
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
	fflush(stdout);

	MULTIUNLOCK();
}

void PRINT_STATUSES_RESET()
{
	while(statuses.size>0)
	{
		free(pop(&statuses));
	}
}

void PRINT_STATUS_MULTI_START()
{
	MULTIMODE = TRUE;
	pthread_mutex_init(&LOCK, NULL);
}

void PRINT_STATUS_MULTI_STOP()
{
	MULTIMODE = FALSE;
	pthread_mutex_destroy(&LOCK);
}

void PRINT_STATUS_ORDER_RESET(){
	MULTILOCK();
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
	MULTIUNLOCK();
}
