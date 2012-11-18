#ifndef D_MISSION_H
#define D_MISSION_H

#include "d_weather.h"

#define MSSN_KEY_CLOUD_TYPE         ("CloudType")
#define MSSN_KEY_CLOUD_HEIGTH       ("CloudHeight")

#define MSSN_KEY_TIME               ("TIME")
#define MSSN_KEY_YEAR               ("Year")
#define MSSN_KEY_MONTH              ("Month")
#define MSSN_KEY_DAY                ("Day")

#define MSSN_KEY_WIND_DIRECTION     ("WindDirection")
#define MSSN_KEY_WIND_SPEED         ("WindSpeed")
#define MSSN_KEY_GUST               ("Gust")
#define MSSN_KEY_TURBULENCE         ("Turbulence")

typedef struct D_MISSION
{
    /** Missions name used for comfortable identification */
    char*   name;

    /** Path to file starting from server's "Missions" subdirectory */
    char*   path;

    /** Mission's duration in seconds */
    int     sDuration;

    /** Mission's weather data */
    D_WEATHER_REPORT weather;
} D_MISSION;

#endif // D_MISSION_H
