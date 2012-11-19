#ifndef PILOT_MANAGER_H
#define PILOT_MANAGER_H

#include "util/types.h"

void pm_init();
void pm_teardown();

void pm_user_join(char* callsign, char* ip, uint2 channel);
void pm_user_left(char* callsign);

#endif // PILOT_MANAGER_H
