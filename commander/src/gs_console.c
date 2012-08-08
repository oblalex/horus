#include "gs_console.h"

#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>

#include "gs_cfg.h"
#include "gs_paths.h"
#include "util/print_status.h"
#include "util/file.h"
#include "util/l10n.h"

static int SOCKET_FD = 0;
static struct timeval waitd;
static fd_set read_flags, write_flags;

BOOL gs_console_init()
{
	PRINT_STATUS_NEW(tr("Initializing console socket"));

	sleep(3);

	BOOL result = TRUE;

	while (1)
	{
		SOCKET_FD = socket(AF_INET, SOCK_STREAM, 0);
		if (SOCKET_FD < 0) 
		{
			result = FALSE;
			break;
		}

		struct sockaddr_in addr;
		socket_addr_prepare(&addr);

		if (connect(SOCKET_FD, (struct sockaddr*)&addr, sizeof(addr)) < 0)
		{
			PRINT_STATUS_ORDER_RESET();
			perror (tr("Connection failed"));
			result = FALSE;
		}

		waitd.tv_sec = 2;
		waitd.tv_usec = 0;
		
		FD_ZERO(&read_flags);
		FD_ZERO(&write_flags);
		
		FD_SET(SOCKET_FD, &read_flags);
		FD_SET(SOCKET_FD, &write_flags);
		
		if (select(SOCKET_FD+1, &read_flags, &write_flags, (fd_set*)0, &waitd) <0)
			result = FALSE;

		break;
	}

	if	(result == TRUE)
	{
		PRINT_STATUS_DONE();
	} else {
		PRINT_STATUS_FAIL();
	}

	return result;
}

static void socket_make_nonblocking()
{
	int flags = fcntl(SOCKET_FD, F_GETFL, 0);
	fcntl(SOCKET_FD ,F_SETFL, flags | O_NONBLOCK);
}

static void socket_addr_prepare(struct sockaddr_in* addr)
{
	int len = 40;
	char str_ip[len];
	get_server_ip((char*)str_ip, len);

	(*addr).sin_family 		= AF_INET;
	(*addr).sin_addr.s_addr 	= inet_addr(str_ip);
	(*addr).sin_port 			= htons(atoi(GS_CONSOLE_PORT));
}

#define TMP_IP "tmp_ip"

static void get_server_ip(char* str_ip, int length)
{
	system("lsof -i | grep -m 1 " GS_EXE_NAME_SHORT " | awk '{print $9}' | awk -F: '{print $1}' > " TMP_IP);
	char* path = TMP_IP;
	
	FILE* file = fopen(path, "r");

	if (file != NULL)
	{
		fgets (str_ip, length, file);
		fclose (file);
	}

	unlink(path);
}

void gs_console_tear_down()
{
	if (SOCKET_FD == 0) return;

	PRINT_STATUS_NEW(tr("Closing console socket"));
		
	close(SOCKET_FD);
	SOCKET_FD = 0;

	PRINT_STATUS_DONE();
}

int get_gs_console_socket()
{
	return SOCKET_FD;
}

void console_line_rd(int fd, char* line, int size, int offset, RL_STAT* stat)
{	
	if (wait_rx(fd) < 0) return;
	line_rd(fd, line, size, offset, stat); 
}

void console_line_wr(int fd, char* line, int size)
{	
	wait_tx(fd);
	line_wr(fd, line, size); 
}

static int wait_rx(int sock)
{
	int result = 0;
	FD_ZERO(&read_flags);
	FD_SET(sock, &read_flags);
	result = select(sock+1, &read_flags, NULL, NULL, NULL );
	if ( result == -1 ) return result;
	if ( FD_ISSET(sock, &read_flags) ) return 1;
	return 0;
}

static int wait_tx(int sock)
{
	int result = 0;
	FD_ZERO(&write_flags);
	FD_SET(sock, &write_flags);
	result = select(sock+1, NULL, &write_flags, NULL, NULL );
	if ( result == -1 ) return result;
	if ( FD_ISSET(sock, &write_flags) ) return 1;
	return 0;
}
