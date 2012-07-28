#ifndef UTILS_H
#define UTILS_H

#include <string.h>
#include <time.h>

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

static void waitMs(long secs, long ms){
	struct timespec ts;
    ts.tv_sec = secs;
    ts.tv_nsec = ms * 1000000;
	nanosleep(&ts, NULL);
}

#endif // UTILS_H