#ifndef GS_CMD_H
#define GS_CMD_H

#define GS_CMD_GC 					"GC"
#define GS_CMD_TIMEOUT(DELAY, CMD)	"timeout " DELAY " " CMD
#define GS_CMD_RUN_FILE(FNAME)		"f " FNAME
#define GS_CMD_EXIT 				"exit"

#define GS_CMD_KICK_NUM(NUM)		"kick# " #NUM
#define GS_CMD_KICK_FIRST			GS_CMD_KICK_NUM(1)

#define GS_CMD_CHAT					"chat"
#define GS_CMD_CHAT_ALL				GS_CMD_CHAT " \"%s\" ALL"

void gs_cmd_init(int fd);

void gs_cmd_exit();

void gs_cmd_chat_all(char* msg);

void gs_cmd_kick_all(fd);

#endif // GS_CMD_H
