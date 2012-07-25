#include <stdlib.h>
#include <signal.h>

#include "game_server.h"

void init();
void setup_termination_hooks();
void termination_handler(int);

int main (int argc, char const *argv[])
{
	init();
	exit(EXIT_SUCCESS);
}

void init()
{
	term_init();
	setup_termination_hooks();
	
	game_server_check_path();
}

void setup_termination_hooks()
{
	signal(SIGINT,	termination_handler);
	signal(SIGABRT,	termination_handler);
	signal(SIGTERM,	termination_handler);
}

void termination_handler(int signum)
{
	printf("\n");
	term_style_reset();
}