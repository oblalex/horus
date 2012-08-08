#include <wchar.h>
#include <string.h>
#include <stdio.h>
#include <time.h>
#include <pthread.h>

#include "gs_cmd.h"
#include "gs_cfg.h"
#include "gs_console.h"

#include "util/print_status.h"
#include "util/str.h"

static int GS_IN_FD;
static pthread_mutex_t LOCK;

void gs_cmd_init()
{
	GS_IN_FD = get_gs_console_socket();
	pthread_mutex_init(&LOCK, NULL);
}

void gs_cmd_exit()
{
	gs_cmd_send(GS_CMD_EXIT);
}

void gs_cmd_chat_all(char* msg)
{
	int max_len = GS_CMD_CHAT_MAX_LEN*6;

	char emsg[max_len];
	str_escape_unicode(msg, strlen(msg), emsg, max_len);
	
	char cmd[max_len+20];
	sprintf(cmd, GS_CMD_CHAT_ALL, emsg);
	gs_cmd_send((char*)&cmd);
}

void gs_cmd_kick_all()
{	
	char* msg = "Kicking all!";
	PRINT_STATUS_NEW(msg);	
	
	int i;
	for(i=0; i<3; i++)
	{	
		gs_cmd_chat_all(msg);
		sleep(2);
	}

	char* channels = gs_cfg_get(GS_CFG_SEC_NET, "serverChannels");
	char* cmd = GS_CMD_KICK_FIRST;

	for(i=0; i<atoi(channels); i++)
		gs_cmd_send(cmd);

	PRINT_STATUS_DONE();
}

static void gs_cmd_send(char* cmd)
{
	pthread_mutex_lock(&LOCK);

	console_line_wr(GS_IN_FD, cmd, strlen(cmd));
	usleep(10*1000);

	pthread_mutex_unlock(&LOCK);
}

void gs_cmd_tear_down()
{
	pthread_mutex_destroy(&LOCK);
}
