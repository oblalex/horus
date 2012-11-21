#include <config.h>

#include <wchar.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>

#include "gs_cmd.h"
#include "gs_cfg.h"
#include "sys_cfg.h"

#include "gs_console.h"

#include "util/print_status.h"
#include "util/str.h"

#ifndef _WIN_
    #include <unistd.h>
#endif

static void gs_cmd_send(char* cmd);

static int GS_IN_FD;
static pthread_mutex_t LOCK;

static char*    GREETING_FORMAT;
static char     ABOUT_COMMANDER[255];

void gs_cmd_init()
{
	GS_IN_FD = get_gs_console_socket();
	pthread_mutex_init(&LOCK, NULL);

    GREETING_FORMAT = tr("Hello, %s! Welcome to '%s' server!");
    sprintf(ABOUT_COMMANDER,
            tr("Server is under Horus Commander control (v.%s, oblalex.github.com/horus)."),
            PACKAGE_VERSION);
}

void gs_cmd_exit()
{
	gs_cmd_send(GS_CMD_EXIT);
}

void gs_cmd_chat_all(char* msg)
{
	gs_cmd_chat(msg, CHAT_ADDRESSEE_ALL);
}

void gs_cmd_chat_callsign(char* username, char* msg)
{
#ifdef _WIN_
    // 3 is for "TO "
    char addressee[strlen(username)+3];
    sprintf(addressee, CHAT_ADDRESSEE_USERNAME, username);
#else
    char eusername[strlen(username)*6-5];
    str_escape_unicode(username, strlen(username), eusername, sizeof eusername);

    // 3 is for "TO "
    char addressee[strlen(eusername)+3];
    sprintf(addressee, CHAT_ADDRESSEE_USERNAME, eusername);
#endif
    gs_cmd_chat(msg, addressee);
}

void gs_cmd_chat_usernum(int num, char* msg)
{
	char addressee[15];
	sprintf(addressee, CHAT_ADDRESSEE_USERNUM, num);
	gs_cmd_chat(msg, addressee);
}

void gs_cmd_chat_army(char army_num, char* msg)
{
	char addressee[7];
	sprintf(addressee, CHAT_ADDRESSEE_ARMY, army_num);
	gs_cmd_chat(msg, addressee);
}

void gs_cmd_chat(char* msg, char* addressee)
{
#ifdef _WIN_
    char cmd[strlen(msg)+strlen(addressee)+9];
#else
    char emsg[GS_CMD_CHAT_MAX_LEN*6];

	// 9 is for "chat \"", "\" ", '\0'
    char cmd[(sizeof emsg)+strlen(addressee)+9];
#endif

	char chunk[GS_CMD_CHAT_MAX_LEN*2];
	
	int offset = 0;
	int end = strlen(msg);

	while (offset<end)
	{
		memset(chunk, '\0', sizeof chunk);
		offset = str_copy_symbols(msg, end, GS_CMD_CHAT_MAX_LEN, offset, (char*)&chunk, sizeof chunk);

    #ifdef _WIN_
        sprintf(cmd, GS_CMD_CHAT, chunk, addressee);
    #else
        str_escape_unicode(chunk, strlen(chunk), emsg, sizeof emsg);
		sprintf(cmd, GS_CMD_CHAT, emsg, addressee);
    #endif

        gs_cmd_send((char*)&cmd);
	}
}

void gs_cmd_kick_all()
{	
    char* msg = tr("Kicking all!");
    PRINT_STATUS_NEW(msg);

    uint2 i;
	for(i=0; i<3; i++)
    {
        gs_cmd_chat_all(msg);
		
    #if defined(_WIN_)
		Sleep(2000);
    #else
		sleep(2);
    #endif
	}

    char* cmd = GS_CMD_KICK_FIRST;

    for(i=0; i< gs_cfg_getChannelsCount(); i++)
		gs_cmd_send(cmd);

	PRINT_STATUS_DONE();
}

void gs_cmd_greet_user(char* callsign)
{
    char msg[255];
    sprintf(msg, GREETING_FORMAT, callsign, sys_cfg_getServerName());

    gs_cmd_chat_callsign(callsign, msg);
    gs_cmd_chat_callsign(callsign, sys_cfg_getServerDescr());
    gs_cmd_chat_callsign(callsign, ABOUT_COMMANDER);
}

void gs_cmd_mssn_status()
{
    gs_cmd_send(GS_CMD_MSSN_STAT);
}

void gs_cmd_mssn_load(char* path)
{
    char cmd[255];
    sprintf(cmd, GS_CMD_MSSN_LOAD, path);
    gs_cmd_send(cmd);
}

void gs_cmd_mssn_run()
{
    gs_cmd_send(GS_CMD_MSSN_RUN);
}

void gs_cmd_mssn_end()
{
    gs_cmd_send(GS_CMD_MSSN_END);
}

void gs_cmd_mssn_unload()
{
    gs_cmd_send(GS_CMD_MSSN_UNLOAD);
}

static void gs_cmd_send(char* cmd)
{
    pthread_mutex_lock(&LOCK);

	console_line_wr(GS_IN_FD, cmd, strlen(cmd));

    #if defined(_WIN_)
        Sleep(10);
    #else
        usleep(10*1000);
    #endif

	pthread_mutex_unlock(&LOCK);
}

void gs_cmd_tear_down()
{
	pthread_mutex_destroy(&LOCK);
}
