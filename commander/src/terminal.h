#ifndef TERMINAL_H
#define TERMINAL_H

#include <stdio.h>
#include <signal.h>
#include <sys/ioctl.h>

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

struct winsize TERM_SIZE;

void term_init();
void term_reset();
void updateWindowSizeInfo();
void term_style(int, int, int);

void updateWindowSizeInfo()
{
	ioctl(STDOUT_FILENO, TIOCGWINSZ, &TERM_SIZE);
}

void resizeHandler(int sig)
{
	updateWindowSizeInfo();
}

void term_init()
{
	signal(SIGWINCH, resizeHandler);
	updateWindowSizeInfo();
}

void term_reset()
{
	term_style(TA_NONE, TC_NONE, TC_NONE);
}

void term_style(int attr, int fg, int bg)
{	char command[13];
	sprintf(command, "%c[%d;%d;%dm", 0x1B, attr, fg + 30, bg + 40);
	printf("%s", command);
}

#endif // TERMINAL_H