#include "str.h"
#include <string.h>

char* str_copy(const char* str)
{
    int n = strlen(str) + 1;
    char *dup = malloc(n);
    if (dup != NULL)
    {
        strcpy(dup, str);
    }
    return dup;
}

void str_null_termitate(char* str)
{
	int last_char_id = strlen(str)-1;
	if (str[last_char_id]=='\n') str[last_char_id] = 0;
}
