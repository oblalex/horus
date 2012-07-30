#include <stdio.h>
#include <stdlib.h>
#include <signal.h>

#include "util/print_status.h"

void init();
void setup_termination_hooks();
void termination_handler(int signum);
void foo();

int main (int argc, char const *argv[])
{
	init();
	foo();
	exit(EXIT_SUCCESS);
}

void init()
{
	term_init();
	setup_termination_hooks();
}

void setup_termination_hooks()
{
	signal(SIGPIPE,	SIG_IGN);

	signal(SIGINT,	termination_handler);
	signal(SIGKILL,	termination_handler);
	signal(SIGTERM,	termination_handler);
	signal(SIGABRT,	termination_handler);
}

void termination_handler(int signum)
{		
	printf("\nSignal caught: #%d.\n", signum);

	term_styleReset();
	PRINT_STATUSES_RESET();
	exit(EXIT_SUCCESS);
}

void foo()
{
	int len = 100;
  	char line[len];

	while(1){
		fgets(line, len, stdin);
		fprintf(stderr, line);
	}
}