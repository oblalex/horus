#ifndef GS_REGEX_H
#define GS_REGEX_H

#include "common.h"
#include <regex.h>

BOOL compile_regex(regex_t* r, const char* regex_text);

#endif // GS_REGEX_H 
