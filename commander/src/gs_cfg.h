#ifndef GS_CFG_H
#define GS_CFG_H

#include "gs_cfg_key.h"

#define GS_CONSOLE_PORT "20001"

void gs_cfg();
static void gs_cfg_logging();
static void gs_cfg_logging_chat();
static void gs_cfg_logging_console();
static void gs_cfg_logging_file();
static void gs_cfg_console_connection();
static void gs_cfg_version_checking();

char* gs_cfg_get(const char* sec_name, const char* key);

#endif // GS_CFG_H

