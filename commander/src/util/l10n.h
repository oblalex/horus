#ifndef L10N_H
#define L10N_H

#include <libintl.h>

#define tr(str) gettext(str)

void locale_init(char *code);
void locale_tearDown();

#endif // L10N_H
