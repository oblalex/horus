#include "d_weather.h"
#include <stdlib.h>

void d_weather_init(D_WEATHER_REPORT** _this)
{
    if ((*_this) == NULL)
        (*_this) = (struct D_WEATHER_REPORT*) malloc(sizeof (struct D_WEATHER_REPORT));

    (*_this)->cloudsHeightM     = 0;
    (*_this)->windDirectionDeg  = 0;
    (*_this)->windSpeedMS       = 0;

    (*_this)->gust          = GUST_NONE;
    (*_this)->turbulence    = TURB_NONE;
    (*_this)->weather       = WEATH_CLOUDLESS;

    timestamp_init(&((*_this)->publGameTS));
    timestamp_init(&((*_this)->publRealTS));
}

void d_weather_free(D_WEATHER_REPORT** _this)
{
    if ((*_this) == NULL) return;
    free(*_this);
    *_this = NULL;
}
