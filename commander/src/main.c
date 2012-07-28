#include <stdlib.h>
#include <signal.h>

#include "game_server.h"

void init();
void setup_termination_hooks();
void termination_handler(int signum);
void foo();

int main (int argc, char const *argv[])
{
	init();
	gs_start();	
	foo();
	gs_stop();
	exit(EXIT_SUCCESS);
}

void init()
{
	term_init();
	setup_termination_hooks();
}

void setup_termination_hooks()
{
	signal(SIGINT,	termination_handler);
	signal(SIGKILL,	termination_handler);
	signal(SIGTERM,	termination_handler);
	signal(SIGABRT,	termination_handler);
}

void termination_handler(int signum)
{		
	printf("\n");
	gs_stop();
	term_style_reset();
	PRINT_STATUSES_RESET();	
}

void foo()
{
	printf("Press <Return> to exit.\n");
	getchar();
}