#include "timestamp_t.h"

#include <stdio.h>

void timestamp_init(TIMESTAMP_T* _this)
{
    _this->year          = 0;
    _this->month         = 0;
    _this->day           = 0;

    _this->hour          = 0;
    _this->minute        = 0;
    _this->second        = 0;
    _this->millisecond   = 0;
}

void secondsToTimeString(int seconds, char* str)
{
    int h = seconds/3600;
    int tempM = seconds%3600;
    int m = tempM/60;
    int s = tempM%60;

    sprintf(str, "%02d:%02d:%02d", h, m, s);
}
