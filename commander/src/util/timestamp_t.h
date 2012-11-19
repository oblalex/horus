#ifndef TIMESTAMP_T_H
#define TIMESTAMP_T_H

#include "types.h"

typedef struct
{
    uint2    year;
    uint1    month;
    uint1    day;

    uint1    hour;
    uint1    minute;
    uint1    second;
    uint2    millisecond;
} TIMESTAMP_T;

void secondsToTimeString(int seconds, char* str);

#endif // TIMESTAMP_T_H
