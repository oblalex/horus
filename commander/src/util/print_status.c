#include <config.h>

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "str.h"
#include "print_status.h"

static STACK_STR statuses;
static BOOL STATUS_LAST_OPER_WAS_PUSH = FALSE;

static BOOL MULTIMODE = FALSE;
static pthread_mutex_t LOCK;

#define MULTILOCK() if (MULTIMODE==TRUE) pthread_mutex_lock(&LOCK);
#define MULTIUNLOCK() if (MULTIMODE==TRUE) pthread_mutex_unlock(&LOCK);

void print_status_tail(int color, const char* status)
{
	term_style(TA_BRIGHT, color, TC_NONE);
	
#ifdef _WIN_
    wchar_t* f1 = L"%S";
    int brackets_color = TC_MAGENTA;

    char buf[255];
    CharToOem(status, buf);
    wprintf(f1, buf);
#else
    wchar_t* f1 = L"%s";
    int brackets_color = TC_BLUE;
    wprintf(f1, status);
#endif

    term_style(TA_BRIGHT, brackets_color, TC_NONE);
	wprintf(L"]");
	term_styleReset();
}
	
void print_status_raw(char* str, int color, const char* status)
{

#ifdef _WIN_
    int brackets_color = TC_MAGENTA;
#else
    int brackets_color = TC_BLUE;
#endif

    term_style(TA_BRIGHT, brackets_color, TC_NONE);
	
#ifdef _WIN_
	wchar_t* f1 = L"%*S ";	
#else
	wchar_t* f1 = L"%*s ";	
#endif

	wprintf(f1, STATUS_HEAD_INDENT, STATUS_MSG_HEAD);
	term_style(TA_BRIGHT, TC_NONE, TC_NONE);
	
#ifdef _WIN_
	wchar_t* f2 = L"%-*S";	
#else
	wchar_t* f2 = L"%-*s";	
#endif

	// 5 is for length of ":: " + length of "[]"
#ifdef _WIN_
    char buf[255];
    CharToOem(str, buf);
    wprintf(f2, term_getWidth()-5-mbstowcs(NULL, status, 0)-STATUS_HEAD_INDENT_PRIME, buf);
#else
    wprintf(f2, term_getWidth()-5-mbstowcs(NULL, status, 0)-STATUS_HEAD_INDENT_PRIME, str);
#endif
    term_style(TA_BRIGHT, brackets_color, TC_NONE);

    wprintf(L"[");
    print_status_tail(color, status);
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
	
void print_status_finished(int color, const char* status)
{
	MULTILOCK();

	char* str = pop(&statuses);
	
	if (STATUS_LAST_OPER_WAS_PUSH == FALSE)
	{
		print_status_raw(str, color, status);
	} else {
		int rmLen = mbstowcs(NULL, status, 0);
		
	// erase STATUS_BUSY msg
	#ifdef _WIN_
		CONSOLE_SCREEN_BUFFER_INFO csbi;
		GetConsoleScreenBufferInfo(GetStdHandle( STD_OUTPUT_HANDLE ), &csbi);

		COORD coord;
		coord.X = csbi.dwSize.X-rmLen-1;
		coord.Y = csbi.dwCursorPosition.Y-1;

		SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE),coord);
	#else
		int i;
		for (i=0; i<rmLen; i++)
		{
			wprintf(L"\b");
		}
	#endif
	
		print_status_tail(color, status);
	}
	
#ifndef _WIN_
	wprintf(L"\n");
#endif
	
	free(str);
	STATUS_LAST_OPER_WAS_PUSH = FALSE;
	fflush(stdout);

	MULTIUNLOCK();
}

void print_status_msg(int color, char* str, BOOL do_indent)
{
	MULTILOCK();

#ifdef _WIN_
    char buf[255];
    CharToOem(str, buf);
#else
	if (STATUS_LAST_OPER_WAS_PUSH == TRUE)
	{
		wprintf(L"\n");
	}
#endif

	term_style(TA_NONE, color, TC_NONE);
	if (do_indent == TRUE)
	{
	
    #ifdef _WIN_
        wchar_t* f1 = L"%*S%S\n";
        wprintf(f1, STATUS_MSG_INDENT, "", buf);
    #else
        wchar_t* f1 = L"%*s%s\n";
        wprintf(f1, STATUS_MSG_INDENT, "", str);
    #endif
	} else {
	
    #ifdef _WIN_
        wchar_t* f1 = L"%S\n";
        wprintf(f1, buf);
    #else
        wchar_t* f1 = L"%s\n";
        wprintf(f1, str);
    #endif
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
