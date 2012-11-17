#include <locale.h>
#include <config.h>
#include <string.h>
#include "common.h"

#ifdef _WIN_
    #include <windows.h>
    static UINT CP_OLD;
    static char* LC_ALL_OLD;

    #define LC_ALL_VAR "LC_ALL"
#endif

#include "l10n.h"

#define LC_COUNT 2
static char* locales[LC_COUNT] = {
#ifdef _WIN_
    "en_EN",
    "ru_RU"
#else
    "en_EN.UTF-8",
    "ru_RU.UTF-8"
#endif
};

void locale_init(char* code)
{
    int locale_id = get_locale_id(code);

#ifdef _WIN_
    CP_OLD = GetConsoleCP();
    if (CP_OLD == 866)
        SetConsoleCP(1251);

    LC_ALL_OLD = getenv(LC_ALL_VAR);

    char var[20];
    sprintf(var, LC_ALL_VAR "=%s\0", locales[locale_id]);
    putenv(var);
#else
    setlocale(LC_ALL, locales[locale_id]);
#endif

	bindtextdomain(PACKAGE, LOCALEDIR);
	textdomain(PACKAGE);
}

int get_locale_id(char *code)
{
    if (code == NULL) return 0;

    BOOL found = FALSE;
    int i;
    int j;
    for (i = 0; i < LC_COUNT; ++i)
    {
        for (j = 0; j < strlen(code); ++j)
            if (locales[i][j] == code[j])
            {
                found = TRUE;
            } else {
                found = FALSE;
                continue;
            }
    }
    if (found==TRUE)
        return i-1;
    else
        return 0;
}

void locale_tearDown()
{
#ifdef _WIN_
    SetConsoleCP(CP_OLD);
    if (LC_ALL_OLD != NULL)
    {
        char var[20];
        sprintf(var, LC_ALL_VAR "=%s\0", LC_ALL_OLD);
        putenv(var);
    }
#endif
}
