#include "pilot_manager.h"
#include "util/print_status.h"

void pm_init()
{
    PRINT_STATUS_NEW(tr("Pilot manager initialization"));

    // TODO:

    PRINT_STATUS_DONE();
}

void pm_teardown()
{
    PRINT_STATUS_NEW(tr("Pilot manager tearing down"));

    // TODO:

    PRINT_STATUS_DONE();
}

void pm_user_join(char* callsign, char* ip, uint2 channel)
{
    // TODO:
}

void pm_user_left(char* callsign)
{
    // TODO:
}
