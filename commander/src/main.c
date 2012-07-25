#include <stdlib.h>
#include "game_server.h"

void init();

int main (int argc, char const *argv[])
{
	init();
	exit(EXIT_SUCCESS);
}

void init()
{
	term_init();
	game_server_check_path();
}