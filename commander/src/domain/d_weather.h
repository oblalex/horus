#ifndef D_WEATHER_H
#define D_WEATHER_H

#include "../util/types.h"
#include "../util/timestamp_t.h"

typedef enum
{
    GUST_NONE,
    GUST_WEAK,
    GUST_AVERAGE,
    GUST_STRONG
} D_GUST;

typedef enum
{
    TURB_NONE,
    TURB_WEAK,
    TURB_AVERAGE,
    TURB_STRONG,
    TURB_VERY_STRONG
} D_TURBULENCE;

typedef enum
{
    WEATH_CLOUDLESS,
    WEATH_CLEAR,
    WEATH_HAZE,
    WEATH_SLIGHT_FOG,
    WEATH_FOG,
    WEATH_PRECIPITATION,
    WEATH_THUNDER
} D_WEATHER;

typedef struct D_WEATHER_REPORT
{
    /** Clouds height in meters */
    uint2 cloudsHeightM;

    /** Wind direction in degrees */
    uint2 windDirectionDeg;

    /** Wind speed in meters per second */
    uint2 windSpeedMS;

    /** In-game publication timestamp */
    TIMESTAMP_T publGameTS;

    /** Real publication timestamp */
    TIMESTAMP_T publRealTS;

    D_GUST gust;
    D_TURBULENCE turbulence;
    D_WEATHER weather;
} D_WEATHER_REPORT;

void d_weather_init(D_WEATHER_REPORT** _this);
void d_weather_free(D_WEATHER_REPORT** _this);

#endif // D_WEATHER_H
