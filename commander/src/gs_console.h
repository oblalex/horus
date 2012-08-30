#ifndef GS_CONSOLE_H
#define GS_CONSOLE_H

#if defined(_WIN_)
	#include <winsock2.h>
#else
	#include <netinet/in.h>
#endif

#include "util/common.h"
#include "util/file.h"

BOOL gs_console_init();
static void get_server_ip(char* str_ip, int length);
static void socket_make_nonblocking();
static void socket_addr_prepare(struct sockaddr_in* addr);

void gs_console_tear_down();
int get_gs_console_socket();

void console_line_rd(int fd, char* line, int size, int offset, RL_STAT* stat);
void console_line_wr(int fd, char* line, int size);

static int wait_rx(int sock);
static int wait_tx(int sock);

#endif // GS_CONSOLE_H
