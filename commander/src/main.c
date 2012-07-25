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
	if (signal (SIGINT, termination_handler) == SIG_IGN)
		signal (SIGINT, SIG_IGN);
	if (signal (SIGHUP, termination_handler) == SIG_IGN)
		signal (SIGHUP, SIG_IGN);
	if (signal (SIGTERM, termination_handler) == SIG_IGN)
		signal (SIGTERM, SIG_IGN);
}

void termination_handler(int signum)
{
	term_style_reset();
}