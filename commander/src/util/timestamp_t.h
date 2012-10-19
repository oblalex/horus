#ifndef TIMESTAMP_T_H
#define TIMESTAMP_T_H

typedef struct
{
    unsigned int    year;
    unsigned int    month;
    unsigned int    day;

    unsigned int    hour;
    unsigned int    minute;
    unsigned int    second;
    unsigned int    millisecond;
} TIMESTAMP_T;

void secondsToTimeString(int seconds, char* str);

#endif // TIMESTAMP_T_H
