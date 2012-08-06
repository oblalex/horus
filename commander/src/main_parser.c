#include "main_parser.h"

#include <string.h>
#include <wchar.h>

#include "config.h"
#include "util/l10n.h"
#include "util/terminal.h"

/**
 * Parsing arguments is splitted into 2 steps: recognition and action on
 * successful recognition. This is so, because action may require more
 * than 1 argument. This means that arguments' iterator may change with
 * step more than 1.
 */
void parseArgs(int argc, char const *argv[])
{
	int i;
	for (i=1; i<argc; i++)
	{
		if (recon_version(argv[i]) == TRUE)
		{
			print_version();
		} else if (recon_help(argv[i]) == TRUE) {
			print_help(basename(argv[0]));
		} else non_recon(argv[i]);
	}
}

static BOOL recon_version(char const *arg)
{
	return (   (strcmp(arg, "-v") == 0)
			|| (strcmp(arg, "--version") == 0)) ? TRUE : FALSE;
}

static void print_version()
{
	wprintf(L"%s (%s)\n",	PACKAGE_NAME, PACKAGE_VERSION);
}

static BOOL recon_help(char const *arg)
{
	return (   (strcmp(arg, "-h") == 0)
			|| (strcmp(arg, "--help") == 0)) ? TRUE : FALSE;
}

static void print_help(char const *exec_name)
{
	print_version();
	wprintf(L"%s.\n",
			tr("\"IL-2 Sturmovik: Fogotten Battles\" server control and statistics collecting subsystem of \"Horus\""));
	
	wprintf(L"\n%s: %s [arguments]\n", tr("Usage"), exec_name);
	
	wprintf(L"\n%s:\n", tr("Arguments"));
	wprintf(L"\t%s, %s\t\t%s\n", ARG_HELP_SHORT, ARG_HELP,
			tr("Print this help"));
	wprintf(L"\t%s, %s\t\t%s\n", ARG_VERSION_SHORT, ARG_VERSION,
			tr("Print program version"));

	wprintf(L"\n%40s: %s", tr("Send bug reports to"), PACKAGE_BUGREPORT);
	wprintf(L"\n%40s: %s\n\n", tr("More information at"), PACKAGE_URL);
}

static void non_recon(char const *arg)
{	
	term_style(TA_NONE, TC_RED, TC_NONE);
	wprintf(L"%s: \"%s\".\n", tr("Argument unknown"), arg);
	term_styleReset();
}
