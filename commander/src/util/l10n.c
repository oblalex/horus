#include <locale.h>
#include <config.h>

#ifdef _WIN_
    #include <windows.h>
    UINT CP_OLD;
#endif

#include "l10n.h"

void locale_init()
{
	setlocale(LC_ALL, "");

#ifdef _WIN_
    CP_OLD = GetConsoleCP();
    if (CP_OLD == 866)
        SetConsoleCP(1251);
#endif

	bindtextdomain(PACKAGE, LOCALEDIR);
	textdomain(PACKAGE);
}

void locale_tearDown()
{
#ifdef _WIN_
    SetConsoleCP(CP_OLD);
#endif
}
