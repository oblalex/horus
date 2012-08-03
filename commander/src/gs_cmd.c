#include <wchar.h>
#include <string.h>
#include <stdio.h>

#include "gs_cmd.h"
#include "gs_cfg.h"

#include "util/common.h"
#include "util/file.h"

int GS_IN_FD;

void gs_cmd_init(int fd)
{
	GS_IN_FD = fd;
}

void gs_cmd_exit()
{
	char* cmd = GS_CMD_EXIT CH_NULL;
	line_wr(GS_IN_FD, cmd, strlen(cmd));
}

void gs_cmd_chat_all(char* msg)
{
	char cmd[120];
	sprintf(&cmd, GS_CMD_CHAT_ALL CH_NULL, msg);
	line_wr(GS_IN_FD, cmd, strlen(cmd));
}

void gs_cmd_kick_all()
{
	char* channels = gs_cfg_get(GS_CFG_SEC_NET, "serverChannels");
	char* cmd = GS_CMD_KICK_FIRST CH_NULL;
	int i;

	gs_cmd_chat_all("Kicking all");
	for(i=0; i<atoi(channels); i++)
		line_wr(GS_IN_FD, cmd, strlen(cmd));
}
