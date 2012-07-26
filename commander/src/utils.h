#ifndef UTILS_H
#define UTILS_H

#include <string.h>

static char* strCopy(const char* str)
{
    int n = strlen(str) + 1;
    char *dup = malloc(n);
    if(dup)
    {
        strcpy(dup, str);
    }
    return dup;
}

#endif // UTILS_H