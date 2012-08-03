#include <stdlib.h>

#include "util/print_status.h"
#include "util/l10n.h"
#include "gs.h"

void init();
void tearDown();

int main (int argc, char const *argv[])
{

	init();
	gs_init();
	gs_run();
	tearDown();
	exit(EXIT_SUCCESS);
}

void init()
{
	locale_init();
	term_init();
}

void tearDown()
{
	term_styleReset();
	PRINT_STATUSES_RESET();
}

