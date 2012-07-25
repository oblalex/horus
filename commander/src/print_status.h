#ifndef PRINT_STATUS_H
#define PRINT_STATUS_H

#include "terminal.h"

#define STATUS_MSG_BUSY "BUSY"
#define STATUS_MSG_DONE "DONE"
#define STATUS_MSG_FAIL "FAIL"

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
	printf(" :: ");
	term_style(TA_BRIGHT, TC_NONE, TC_NONE);
	printf("%-*s", TERM_SIZE.ws_col-10, str); // 10 is for length of " :: " and length of status msg in brackets
	term_style(TA_BRIGHT, TC_BLUE, TC_NONE);
	printf("[");
	print_status_tail(color, msg);
}

#define PRINT_STATUS_NEW(STR) print_status_raw(STR, TC_YELLOW, STATUS_MSG_BUSY);
	
void print_status_finished(int color, char* msg)
{
	printf("\b\b\b\b"); //erase "BUSY" msg
	print_status_tail(color, msg);
	printf("\n");
}

#define PRINT_STATUS_DONE print_status_finished(TC_GREEN, STATUS_MSG_DONE);
#define PRINT_STATUS_FAIL print_status_finished(TC_RED,   STATUS_MSG_FAIL);

#endif // PRINT_STATUS_H