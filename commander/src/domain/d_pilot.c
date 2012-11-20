#include "d_pilot.h"
#include <stdlib.h>
#include <string.h>

void d_pilot_init(D_PILOT** _this, char* callsign)
{
    if ((*_this) != NULL) return;

    (*_this) = (D_PILOT*)malloc(sizeof(D_PILOT));

    (*_this)->callsign        = (char*)malloc(sizeof(char)*strlen(callsign)+1);
    strcpy((*_this)->callsign, callsign);

    (*_this)->tailNumber    = NULL;
    (*_this)->banned        = FALSE;
    (*_this)->flyTime       = 0;
    (*_this)->idleTime      = 0;
    (*_this)->lifeNumber    = 0;
    (*_this)->score         = 0;
}

void d_pilot_free(D_PILOT** _this)
{
    if ((*_this) == NULL) return;

    if (((*_this)->tailNumber) != NULL)
        free((*_this)->tailNumber);

    free((*_this)->callsign);
    free(*_this);
}
