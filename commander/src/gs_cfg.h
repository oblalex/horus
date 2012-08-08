#ifndef GS_CFG_H
#define GS_CFG_H

#include "util/ini.h"

/**
 * @defgroup GS_CFG_SECTIONS Game server's config sections
 * @{  
 */
#define GS_CFG_SEC_CHAT		"chat"
#define GS_CFG_SEC_GAME 	"game"
#define GS_CFG_SEC_CONSOLE 	"Console"
#define GS_CFG_SEC_NET		"NET"
/**@}*/

#define GS_CONSOLE_PORT		"20001"

void gs_cfg();
void gs_cfg_logging(INI_CONTAINER* cfg);
void gs_cfg_logging_chat(INI_CONTAINER* cfg);
void gs_cfg_logging_console(INI_CONTAINER* cfg);
void gs_cfg_logging_file(INI_CONTAINER* cfg);
void gs_cfg_console_connection(INI_CONTAINER* cfg);
void gs_cfg_version_checking(INI_CONTAINER* cfg);

char* gs_cfg_get(const char* sec_name, const char* key);

#endif // GS_CFG_H

