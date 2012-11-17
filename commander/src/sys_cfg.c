#include "sys_cfg.h"
#include "gs_paths.h"
#include "util/ini.h"

char* sys_cfg_get(const char* sec_name, const char* key)
{
    INI_CONTAINER* cfg = ini_start((char*)PATH_SYS_CFG);
    char* value = ini_value_get(cfg, sec_name, key);
    ini_end(cfg);
    return value;
}
