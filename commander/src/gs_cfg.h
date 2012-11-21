#ifndef GS_CFG_H
#define GS_CFG_H

#include "util/types.h"
#include "gs_cfg_key.h"

#define GS_CONSOLE_PORT "20001"

void gs_cfg_init();
void gs_cfg_teardown();

uint2 gs_cfg_getChannelsCount();
char* gs_cfg_getServerName();
char* gs_cfg_getServerDescr();

char* gs_cfg_get(const char* sec_name, const char* key);

#endif // GS_CFG_H

