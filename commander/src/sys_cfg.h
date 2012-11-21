#ifndef SYS_CFG_H
#define SYS_CFG_H

#include "sys_cfg_key.h"

void sys_cfg_init();
void sys_cfg_teardown();

char* sys_cfg_getServerName();
char* sys_cfg_getServerDescr();

char* sys_cfg_get(const char* sec_name, const char* key);

#endif // SYS_CFG_H
