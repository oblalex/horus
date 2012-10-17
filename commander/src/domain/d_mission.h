#ifndef D_MISSION_H
#define D_MISSION_H

#include "d_weather.h"

typedef struct D_MISSION_LITE
{
    /** Missions name used for comfortable identification */
    char*   name;

    /** Path to file starting from server's "Missions" subdirectory */
    char*   path;

    /** Mission's duration in seconds */
    int     sDuration;

    /** Mission's weather data */
    D_WEATHER_REPORT weather;

    /** Mission that will be played after red team wons current mission */
    struct D_MISSION_LITE* nextRed;

    /** Mission that will be played after blue team wons current mission */
    struct D_MISSION_LITE* nextBlue;

    /** Mission that will be played after no team wons current mission or nextRead and nextBlue are unset */
    struct D_MISSION_LITE* next;
} D_MISSION_LITE;

#endif // D_MISSION_H
