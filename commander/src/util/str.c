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