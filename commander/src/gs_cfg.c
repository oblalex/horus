#include <stdlib.h>

#include "gs_cfg.h"
#include "gs_paths.h"

#include "util/ini.h"
#include "util/l10n.h"
#include "util/print_status.h"

static void gs_cfg_logging();
static void gs_cfg_logging_chat();
static void gs_cfg_logging_console();
static void gs_cfg_logging_file();
static void gs_cfg_console_connection();
static void gs_cfg_version_checking();

static INI_CONTAINER* cfg;

void gs_cfg()
{
    PRINT_STATUS_NEW(tr("Checking server's configuration"));

    PRINT_STATUS_MSG(tr("Loading configuration"));
    cfg = ini_start((char*)PATH_GS_CFG);

    char* err_msg = NULL;

    while(1)
    {
        if (cfg == NULL)
		{
            err_msg = INI_MALLOC_ERR_CONTAINER;
            break;
        }
        if ((err_msg = cfg->error_msg) != NULL) break;

        gs_cfg_logging();
        gs_cfg_console_connection();
        gs_cfg_version_checking();

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

void gs_cfg_logging()
{
	gs_check_path_logs();
    gs_cfg_logging_chat();
    gs_cfg_logging_console();
    gs_cfg_logging_file();
}

void gs_cfg_logging_chat()
{
    PRINT_STATUS_MSG(tr("Suppressing logging to chat"));
    ini_value_set(cfg, GS_CFG_GRP_CHAT, GS_CFG_KEY_AUTO_LOG_DETAIL_NO_GRP, "0");
}

void gs_cfg_logging_console()
{
    PRINT_STATUS_MSG(tr("Disabling saving console output to file"));
    ini_value_set(cfg, GS_CFG_GRP_CONSOLE, GS_CFG_KEY_LOG_NO_GRP, GS_CFG_FALSE);
}

void gs_cfg_logging_file()
{
    PRINT_STATUS_MSG(tr("Setting logging output file"));
    ini_value_set(cfg, GS_CFG_GRP_GAME, GS_CFG_KEY_EVENTLOG_NO_GRP, GS_LOG_EVT);

    PRINT_STATUS_MSG(tr("Enabling log resetting for every mission"));
    ini_value_set(cfg, GS_CFG_GRP_GAME, GS_CFG_KEY_EVENTLOG_KEEP_NO_GRP, GS_CFG_FALSE);

    PRINT_STATUS_MSG(tr("Enabling buildings destruction logging"));
    ini_value_set(cfg, GS_CFG_GRP_GAME, GS_CFG_KEY_EVENTLOG_HOUSE_NO_GRP, GS_CFG_TRUE);
}

void gs_cfg_console_connection()
{
    PRINT_STATUS_MSG(tr("Enabling server's console"));
    ini_value_set(cfg, GS_CFG_GRP_CONSOLE, GS_CFG_KEY_IP_NO_GRP, GS_CONSOLE_PORT);

    PRINT_STATUS_MSG(tr("Setting console allowed clients list"));
    ini_value_set(cfg, GS_CFG_GRP_CONSOLE, GS_CFG_KEY_IPS_NO_GRP, "127.0.0.1");
}

void gs_cfg_version_checking()
{
    PRINT_STATUS_MSG(tr("Setting client version checking"));
    ini_value_set(cfg, GS_CFG_GRP_NET, GS_CFG_KEY_CHECK_RUNTIME_NO_GRP, "1");
}

char* gs_cfg_get(const char* sec_name, const char* key)
{
    INI_CONTAINER* cfg = ini_start((char*)PATH_GS_CFG);
	char* value = ini_value_get(cfg, sec_name, key); 
	ini_end(cfg);
	return value;
}
