#ifndef L10N_H
#define L10N_H

#include <libintl.h>

#define tr(str) gettext(str)

void locale_init();

#endif // L10N_H
