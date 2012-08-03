#include <stdlib.h>

#include "gs_cfg.h"
#include "gs_paths.h"

#include "util/print_status.h"
#include "util/l10n.h"

void gs_cfg()
{
    PRINT_STATUS_NEW(tr("Checking server's configuration"));

    PRINT_STATUS_MSG(tr("Loading configuration"));
    INI_CONTAINER* cfg = ini_start((char*)PATH_GS_CFG);

    const char* err_msg = NULL;

    while(1)
    {
        if (cfg == NULL)
		{
            err_msg = INI_MALLOC_ERR_CONTAINER;
            break;
        }
        if ((err_msg = cfg->error_msg) != NULL) break;

		gs_cfg_logging(cfg);
		gs_cfg_console_connection(cfg);
		gs_cfg_version_checking(cfg);

        PRINT_STATUS_MSG(tr("Saving configuration"));
        ini_end(cfg);
        err_msg = cfg->error_msg;
        break;
    }

    if (err_msg == NULL)
	{
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_MSG_ERR(err_msg);
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }
}

void gs_cfg_logging(INI_CONTAINER* cfg)
{
	gs_check_path_logs();
    gs_cfg_logging_chat(cfg);
    gs_cfg_logging_console(cfg);
    gs_cfg_logging_file(cfg);
}

void gs_cfg_logging_chat(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG(tr("Suppressing logging to chat"));
    ini_value_set(cfg, GS_CFG_SEC_CHAT, "autoLogDetail", "0");
}

void gs_cfg_logging_console(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG(tr("Disabling saving console output to file"));
    ini_value_set(cfg, GS_CFG_SEC_CONSOLE, "LOG", "0");
}

void gs_cfg_logging_file(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG(tr("Setting logging output file"));
    ini_value_set(cfg, GS_CFG_SEC_GAME, "eventlog", GS_LOG_EVT);

    PRINT_STATUS_MSG(tr("Enabling log resetting for every mission"));
    ini_value_set(cfg, GS_CFG_SEC_GAME, "eventlogkeep", "0");

    PRINT_STATUS_MSG(tr("Enabling buildings destruction logging"));
    ini_value_set(cfg, GS_CFG_SEC_GAME, "eventlogHouse", "1");
}

void gs_cfg_console_connection(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG(tr("Disabling server's console"));
    ini_value_set(cfg, GS_CFG_SEC_CONSOLE, "IP", "");

    PRINT_STATUS_MSG(tr("Cleaning console allowed clients list"));
    ini_value_set(cfg, GS_CFG_SEC_CONSOLE, "IPS", "");
}

void gs_cfg_version_checking(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG(tr("Setting client version checking"));
    ini_value_set(cfg, GS_CFG_SEC_NET, "checkRuntime", "1");
}

char* gs_cfg_get(const char* sec_name, const char* key)
{
    INI_CONTAINER* cfg = ini_start((char*)PATH_GS_CFG);
	char* value = ini_value_get(cfg, sec_name, key); 
	ini_end(cfg);
	return value;
}
