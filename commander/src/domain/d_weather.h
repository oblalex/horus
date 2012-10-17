#ifndef D_WEATHER_H
#define D_WEATHER_H

enum D_GUST
{
    NONE,
    WEAK,
    AVERAGE,
    STRONG
};

enum D_TURBULENCE
{
    NONE,
    WEAK,
    AVERAGE,
    STRONG,
    VERY_STRONG
};

enum D_WEATHER
{
    CLOUDLESS,
    CLEAR,
    HAZE,
    SLIGHT_FOG,
    FOG,
    PRECIPITATION,
    THUNDER
};

typedef struct
{
    /** Clouds height in meters */
    int cloudsHeightM;

    /** Wind direction in degrees */
    int windDirectionDeg;

    /** Wind speed in meters per second */
    short windSpeedMS;

    /** In-game publication timestamp */
    unsigned long int publGameTS;

    /** Real publication timestamp */
    unsigned long int publRealTS;

    D_GUST gust;
    D_TURBULENCE turbulence;
    D_WEATHER weather;
} D_WEATHER_REPORT;

#endif // D_WEATHER_H
