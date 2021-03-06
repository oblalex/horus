#ifndef TIMESTAMP_T_H
#define TIMESTAMP_T_H

#include "types.h"

typedef struct TIMESTAMP_T
{
    uint2    year;
    uint1    month;
    uint1    day;

    uint1    hour;
    uint1    minute;
    uint1    second;
    uint2    millisecond;
} TIMESTAMP_T;

void timestamp_init(TIMESTAMP_T* _this);
void secondsToTimeString(int seconds, char* str);

#endif // TIMESTAMP_T_H
