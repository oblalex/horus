#ifndef L10N_H
#define L10N_H

#include <libintl.h>

#define tr(str) gettext(str)

void locale_init(char *code);
static int get_locale_id(char *code);
void locale_tearDown();

#endif // L10N_H
