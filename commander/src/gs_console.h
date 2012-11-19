#ifndef GS_CONSOLE_H
#define GS_CONSOLE_H

#include <config.h>

#if defined(_WIN_)
	#include <winsock2.h>
#else
	#include <arpa/inet.h>
	#include <netinet/in.h>
	#include <sys/socket.h>
	#include <sys/types.h>
#endif

#include "util/common.h"
#include "util/file.h"

BOOL gs_console_init();

void gs_console_tear_down();
int get_gs_console_socket();

void console_line_rd(int fd, char* line, int size, int offset, RL_STAT* stat);
void console_line_wr(int fd, char* line, int size);

#endif // GS_CONSOLE_H
