#ifndef D_PILOT_H
#define D_PILOT_H

#include "../util/common.h"
#include "../util/types.h"

typedef struct D_PILOT
{
    /** Pilot's callsign used on game server */
    char*   callsign;

    /** Aircraft's tail number */
    char*   tailNumber;

    /** Pilot's score */
    uint4   score;

    /** Current life number */
    uint4   lifeNumber;

    /** Pilot's all fly time in seconds */
    uint4   flyTime;

    /** Seconds to wait before take-off */
    uint4   idleTime;

    /** Defines if pilot is allowed to connect to servers */
    BOOL    banned;
} D_PILOT;

void d_pilot_init(D_PILOT** _this, char* callsign);
void d_pilot_free(D_PILOT** _this);

#endif // D_PILOT_H
