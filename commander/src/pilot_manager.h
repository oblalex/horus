#ifndef PILOT_MANAGER_H
#define PILOT_MANAGER_H

#include "util/types.h"
#include "domain/d_pilot.h"

#define PM_REQ_USR_JOIN     0
#define PM_REQ_USR_LEFT     1
#define PM_REQ_MTL          2

typedef struct D_PILOT_ELEM
{
    D_PILOT* data;

    char* IP;

    uint2 channel;

    uint1 side;

    struct D_PILOT_ELEM* next;

} D_PILOT_ELEM;

void pm_init();
void pm_teardown();

void pm_elem_init(D_PILOT_ELEM** _this, char *callsign, char *ip, uint2 channel);
void pm_elem_free(D_PILOT_ELEM** _this);

void pm_user_join_req(char* callsign, char* ip, uint2 channel);
void pm_user_left_req(uint2 channel);

void pm_mtl_req(char* callsign);

uint2 pm_pilot_count();
uint2 pm_pilots_list(char*** callsignes);

#endif // PILOT_MANAGER_H
