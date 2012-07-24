#include <signal.h>
#include <string.h>
#include <sys/ioctl.h>
#include <stdio.h>
#include <unistd.h>

struct winsize WINDOW_SIZE;

#define STR(VAL) #VAL

#define GET_4TH_ARG(arg1, arg2, arg3, arg4, ...) arg4
#define GET_5TH_ARG(arg1, arg2, arg3, arg4, arg5, ...) arg5

// Terminal Colors
#define TC_BLACK	0
#define TC_RED		1
#define TC_GREEN	2
#define TC_YELLOW	3
#define TC_BLUE		4
#define TC_MAGENTA	5
#define TC_CYAN		6
#define TC_WHITE	7
#define TC_NONE		8

// Terminal Attributes
#define TA_NONE		0
#define TA_BRIGHT 	1
#define TA_DIM		2
#define TA_UNDERLINE	3
#define TA_BLINK	4
#define TA_REVERSE	7
#define TA_HIDDEN	8

// Terminal Style helpers
#define TS_PREF	"\x1b["
#define TS_SUFF	"m"
#define TS_SEP	";"

#define T_ATTR_1_ARGS(ATTR)		TS_PREF STR(ATTR) TS_SUFF
#define T_ATTR_2_ARGS(      FG, BG)	TS_PREF           TS_SEP STR(3) STR(FG) TS_SEP STR(4) STR(BG) TS_SUFF
#define T_ATTR_3_ARGS(ATTR, FG, BG)	TS_PREF STR(ATTR) TS_SEP STR(3) STR(FG) TS_SEP STR(4) STR(BG) TS_SUFF
#define T_ATTR_MACRO_CHOOSER(...) GET_4TH_ARG(__VA_ARGS__, T_ATTR_3_ARGS, T_ATTR_2_ARGS, T_ATTR_1_ARGS)

#define T_ATTR(...) T_ATTR_MACRO_CHOOSER(__VA_ARGS__)(__VA_ARGS__)

#define TA_SET(...) printf(T_ATTR(__VA_ARGS__));
#define TA_RESET TA_SET(TA_NONE)

#define CPRINT_A(  ATTR,         FMT, ...) printf(T_ATTR(ATTR        ) FMT T_ATTR(TA_NONE), ##__VA_ARGS__);
#define CPRINT_FB(       FG, BG, FMT, ...) printf(T_ATTR(      FG, BG) FMT T_ATTR(TA_NONE), ##__VA_ARGS__);
#define CPRINT_AFB(ATTR, FG, BG, FMT, ...) printf(T_ATTR(ATTR, FG, BG) FMT T_ATTR(TA_NONE), ##__VA_ARGS__);
#define CPRINT_MACRO_CHOOSER(...) GET_5TH_ARG(__VA_ARGS__, CPRINT_AFB, CPRINT_FB, CPRINT_A)

#define CPRINT(...) CPRINT_MACRO_CHOOSER(__VA_ARGS__)(__VA_ARGS__)
#define CPRINTLN(...) CPRINT(__VA_ARGS__); printf("\n");

#define STATUS_MSG_BUSY "BUSY"
#define STATUS_MSG_DONE "DONE"
#define STATUS_MSG_FAIL "FAIL"

#define PRINT_STATUS_TAIL(COLOR, MSG) \
	CPRINT(TA_BRIGHT, COLOR, TC_NONE, MSG) \
	CPRINT(TA_BRIGHT, TC_BLUE, TC_NONE, "]")

#define PRINT_STATUS_NEW(STR) \
	CPRINT(TA_BRIGHT, TC_BLUE, TC_NONE, " :: ") \
	TA_SET(TA_BRIGHT) \
	printf("%-*s", WINDOW_SIZE.ws_col-10, STR); /* 10 is for length of " :: " and length of status msg in brackets */ \
	CPRINT(TA_BRIGHT, TC_BLUE, TC_NONE, "[") \
	PRINT_STATUS_TAIL(TC_YELLOW, STATUS_MSG_BUSY)

#define PRINT_STATUS_FINISHED(COLOR, MSG) \
	printf("\b\b\b\b"); /* erase "BUSY" msg */ \
	PRINT_STATUS_TAIL(COLOR, MSG) \
	printf("\n");

#define PRINT_STATUS_DONE PRINT_STATUS_FINISHED(TC_GREEN, STATUS_MSG_DONE)
#define PRINT_STATUS_FAIL PRINT_STATUS_FINISHED(TC_RED,   STATUS_MSG_FAIL)

#define SERVER_EXE "../il2server.exe"

void checkDir();
void resizeHandler(int);
void updateWindowSizeInfo();

int main (int argc, char const *argv[])
{
	signal(SIGWINCH, resizeHandler);
	updateWindowSizeInfo();

	checkDir();
	return 0;	
}

void checkDir()
{
	PRINT_STATUS_NEW("Looking for IL-2 server executable");
	
	if( access( SERVER_EXE, X_OK ) != -1 ) {
		PRINT_STATUS_DONE
	} else {
		PRINT_STATUS_FAIL
	}
}

void resizeHandler(int sig)
{
	updateWindowSizeInfo();
}

void updateWindowSizeInfo()
{
	ioctl(STDOUT_FILENO, TIOCGWINSZ, &WINDOW_SIZE);
}
