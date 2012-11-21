#include <config.h>
#include <string.h>
#include <stdlib.h>
#include "sys_cfg.h"
#include "gs_paths.h"
#include "util/ini.h"
#include "util/str.h"

static char* SERVER_NAME;
static char* SERVER_DESCR;

void sys_cfg_init()
{
    INI_CONTAINER* cfg = ini_start((char*)PATH_SYS_CFG);

    char* value;
    SERVER_NAME = NULL;
    value = ini_value_get(cfg, SYS_CFG_GRP_GENERAL, SYS_CFG_KEY_SERVER_NAME_NO_GRP);
    if (value != NULL)
    {
        str_null_termitate(value);

#ifdef _WIN_
        char reEncoded[255];
        utf8_to_cp1251((char*)value, (char*)reEncoded);
        SERVER_NAME = (char*)malloc(sizeof(char)*strlen(reEncoded)+1);
        memcpy(SERVER_NAME, reEncoded, strlen(reEncoded)+1);
#else
        SERVER_NAME = (char*)malloc(sizeof(char)*strlen(value)+1);
        memcpy(SERVER_NAME, value, strlen(value)+1);
#endif
    }

    SERVER_DESCR = NULL;
    value = ini_value_get(cfg, SYS_CFG_GRP_GENERAL, SYS_CFG_KEY_SERVER_DESCR_NO_GRP);
    if (value != NULL)
    {
        str_null_termitate(value);

#ifdef _WIN_
        char reEncoded[255];
        utf8_to_cp1251((char*)value, (char*)reEncoded);
        SERVER_DESCR = (char*)malloc(sizeof(char)*strlen(reEncoded)+1);
        memcpy(SERVER_DESCR, reEncoded, strlen(reEncoded)+1);
#else
        SERVER_DESCR = (char*)malloc(sizeof(char)*strlen(value)+1);
        memcpy(SERVER_DESCR, value, strlen(value)+1);
#endif
    }

    ini_end(cfg);
}

void sys_cfg_teardown()
{
    if (SERVER_NAME     != NULL) free(SERVER_NAME);
    if (SERVER_DESCR    != NULL) free(SERVER_DESCR);
}

char* sys_cfg_getServerName()
{
    return SERVER_NAME;
}

char* sys_cfg_getServerDescr()
{
    return SERVER_DESCR;
}

char* sys_cfg_get(const char* sec_name, const char* key)
{
    INI_CONTAINER* cfg = ini_start((char*)PATH_SYS_CFG);
    char* value = ini_value_get(cfg, sec_name, key);
    ini_end(cfg);
    return value;
}
