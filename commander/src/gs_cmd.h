#ifndef GS_CMD_H
#define GS_CMD_H

#include "util/common.h"

#define GS_CMD_GC 					"GC"
#define GS_CMD_TIMEOUT(DELAY, CMD)	"timeout " DELAY " " CMD
#define GS_CMD_RUN_FILE(FNAME)		"f " FNAME
#define GS_CMD_EXIT 				"exit" NEW_LINE

#define GS_CMD_KICK_NUM(NUM)		"kick# " #NUM NEW_LINE
#define GS_CMD_KICK_FIRST			GS_CMD_KICK_NUM(1)

#define GS_CMD_CHAT_MAX_LEN			80
#define GS_CMD_CHAT					"chat \"%s\" %s" NEW_LINE

#define CHAT_ADDRESSEE_ALL			"ALL"
#define CHAT_ADDRESSEE_USERNAME		"TO %s"
#define CHAT_ADDRESSEE_USERNUM		"TO# %d"
#define CHAT_ADDRESSEE_ARMY			"ARMY %c"

void gs_cmd_init();
void gs_cmd_tear_down();

void gs_cmd_exit();

void gs_cmd_chat_all(char* msg);
void gs_cmd_chat_username(char* username, char* msg);
void gs_cmd_chat_usernum(int num, char* msg);
void gs_cmd_chat_army(char army_num, char* msg);
void gs_cmd_chat(char* msg, char* addressee);

void gs_cmd_kick_all();

static void gs_cmd_send(char* cmd);

#endif // GS_CMD_H