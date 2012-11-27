#include <config.h>

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#include "str.h"
#include "print_status.h"
#include "linenoise.h"

static STACK_STR statuses;
static BOOL STATUS_LAST_OPER_WAS_PUSH = FALSE;

static BOOL MULTIMODE = FALSE;
static pthread_mutex_t LOCK;

static void MULTILOCK();
static void MULTIUNLOCK();

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

void PRINT_STATUS_NEW(char* str, BOOL shLock)
{	
	MULTILOCK();

    if (shLock) linenoiseLock();

	char* str_new = str_copy(str);	
	print_status_raw(str_new, TC_YELLOW, STATUS_MSG_BUSY);
	
	push(&statuses, str_new);
	STATUS_LAST_OPER_WAS_PUSH = TRUE;
	fflush(stdout);

	MULTIUNLOCK();
}

void PRINT_STATUS_DONE(BOOL shUnlock)
{
    print_status_finished(TC_GREEN, (char*)STATUS_MSG_DONE, shUnlock);
}

void PRINT_STATUS_FAIL(BOOL shUnlock)
{
    print_status_finished(TC_RED,   (char*)STATUS_MSG_FAIL, shUnlock);
}
	
void print_status_finished(int color, const char* status, BOOL shUnlock)
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

    if (shUnlock) linenoiseUnlock();

	MULTIUNLOCK();
}

void print_status_msg(int color, char* str, BOOL do_indent, BOOL shLock)
{
	MULTILOCK();

    if (shLock) linenoiseLock();

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

    if (shLock) linenoiseUnlock();

	MULTIUNLOCK();
}

void PRINT_STATUS_MSG(char* msg, BOOL shLock)     {print_status_msg(TC_CYAN, msg, TRUE, shLock);}
void PRINT_STATUS_MSG_WRN(char* msg, BOOL shLock) {print_status_msg(TC_YELLOW,  msg, TRUE, shLock);}
void PRINT_STATUS_MSG_ERR(char* msg, BOOL shLock) {print_status_msg(TC_RED,  msg, TRUE, shLock);}

void PRINT_STATUS_MSG_NOIND(char* msg, BOOL shLock)     {print_status_msg(TC_CYAN, msg, FALSE, shLock);}
void PRINT_STATUS_MSG_WRN_NOIND(char* msg, BOOL shLock) {print_status_msg(TC_YELLOW,  msg, FALSE, shLock);}
void PRINT_STATUS_MSG_ERR_NOIND(char* msg, BOOL shLock) {print_status_msg(TC_RED,  msg, FALSE, shLock);}

void PRINT_STATUSES_RESET()
{
	while(statuses.size>0)
	{
		free(pop(&statuses));
	}
}

void PRINT_STATUS_ORDER_RESET(){
    MULTILOCK();
    STATUS_LAST_OPER_WAS_PUSH = FALSE;
    MULTIUNLOCK();
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

void MULTILOCK()
{
    if (MULTIMODE==TRUE) pthread_mutex_lock(&LOCK);
}

void MULTIUNLOCK()
{
    if (MULTIMODE==TRUE) pthread_mutex_unlock(&LOCK);
}
