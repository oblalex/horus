#include "terminal.h"

#include <unistd.h>
#include <stdio.h>
#include <signal.h>

void term_init()
{
	signal(SIGWINCH, term_resizeHandler);
	term_updateWindowSizeInfo();
	term_styleReset();
}

void term_resizeHandler(int signum)
{
	term_updateWindowSizeInfo();
}

void term_updateWindowSizeInfo()
{
	ioctl(STDOUT_FILENO, TIOCGWINSZ, &TERM_SIZE);
}

void term_style(int attr, int fg, int bg)
{	char command[13];
	sprintf(command, "%c[%d;%d;%dm", 0x1B, attr, fg + 30, bg + 40);
	printf("%s", command);
}

void term_styleReset()
{
	term_style(TA_NONE, TC_NONE, TC_NONE);
}