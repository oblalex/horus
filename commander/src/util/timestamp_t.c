#include "timestamp_t.h"

#include <stdio.h>

void secondsToTimeString(int seconds, char* str)
{
    int h = seconds/3600;
    int tempM = seconds%3600;
    int m = tempM/60;
    int s = tempM%60;

    sprintf(str, "%02d:%02d:%02d", h, m, s);
}
