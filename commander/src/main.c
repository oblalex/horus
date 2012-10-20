#include <stdlib.h>

#include "util/print_status.h"
#include "util/l10n.h"
#include "main_parser.h"
#include "gs.h"

void init();
void tearDown();

int main (int argc, char const** argv)
{
	init();
	if (argc>1)
	{
		parseArgs(argc, argv);
	} else {
		gs_init();
		gs_run();
	}
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
	term_teardown();
	PRINT_STATUSES_RESET();
    locale_tearDown();
}

