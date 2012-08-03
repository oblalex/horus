#include <locale.h>
#include "config.h"

#include "l10n.h"

void locale_init()
{
	setlocale(LC_ALL, "");
	bindtextdomain(PACKAGE, LOCALEDIR);
	textdomain(PACKAGE);
}
