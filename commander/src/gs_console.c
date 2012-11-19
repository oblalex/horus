#include "gs_console.h"

#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <fcntl.h>

#include "gs_cfg.h"
#include "gs_paths.h"
#include "util/print_status.h"
#include "util/l10n.h"

#if defined(_WIN_)
    #include "util/winsock_helper.h"
#else
	#include <errno.h>
	#include <string.h>
    #include "util/file.h"
#endif

static void get_server_ip(char* str_ip, int length);
static void socket_make_nonblocking();
static void socket_addr_prepare(struct sockaddr_in* addr);

static int wait_rx(int sock);
static int wait_tx(int sock);


#if defined(_WIN_)
	static SOCKET SOCKET_FD = INVALID_SOCKET;
#else
	static int SOCKET_FD = 0;
#endif

static struct timeval waitd;
static fd_set read_flags, write_flags;

BOOL gs_console_init()
{
	PRINT_STATUS_NEW(tr("Initializing console socket"));

	BOOL result = TRUE;
	
	while (1)
	{
	#if defined(_WIN_)
		Sleep(2000);
		
		WSADATA wsadata;
		if (WSAStartup(MAKEWORD(2, 2), &wsadata))
		{
			result = FALSE;
			break;
		}
	#else
		sleep(2);
	#endif
		
	#if defined(_WIN_)
		SOCKET_FD = WSASocket(AF_INET, SOCK_STREAM, IPPROTO_TCP, NULL, 0, 0);
		if (SOCKET_FD == INVALID_SOCKET)
	#else
		SOCKET_FD = socket(AF_INET, SOCK_STREAM, 0);
		if (SOCKET_FD < 0)
	#endif
		{
			PRINT_STATUS_MSG_ERR(tr("Socket not created"));
			result = FALSE;
			break;
		}

		struct sockaddr_in addr;
		socket_addr_prepare(&addr);
		socket_make_nonblocking();

	#if defined(_WIN_)
		if ((WSAConnect(SOCKET_FD, (struct sockaddr*)&addr, sizeof(addr), NULL, NULL, NULL, NULL) == SOCKET_ERROR)
			&& (WSAGetLastError() != WSAEWOULDBLOCK))
	#else
		if ((connect(SOCKET_FD, (struct sockaddr*)&addr, sizeof(addr)) < 0) && (errno != EINPROGRESS))
	#endif
		{
			PRINT_STATUS_MSG_ERR(tr("Connection failed"));
			result = FALSE;
			break;
		}
	
		waitd.tv_sec = 2;
		waitd.tv_usec = 0;
		
		FD_ZERO(&read_flags);
		FD_ZERO(&write_flags);
		
		FD_SET(SOCKET_FD, &read_flags);
		FD_SET(SOCKET_FD, &write_flags);

		int res = select(SOCKET_FD+1, &read_flags, &write_flags, (fd_set*)0, &waitd);
		
	#if defined(_WIN_)
		if (res  == SOCKET_ERROR)
	#else
		if (res < 0)
	#endif
		{
			PRINT_STATUS_MSG_ERR(tr("Select failed"));
			result = FALSE;
		}
		
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
#if defined(_WIN_)
	DWORD num_bytes;
	WSAOVERLAPPED overlapped;
	u_long iMode=1;
	
	if (WSAIoctl(SOCKET_FD, FIONBIO, &iMode, sizeof(iMode), NULL, 0, &num_bytes, &overlapped, NULL) == SOCKET_ERROR)
	{
		PRINT_STATUS_MSG_ERR(tr("Cannot make non-blocking"));
	}
	Sleep(1000);
#else
	int flags = fcntl(SOCKET_FD, F_GETFL, 0);
	fcntl(SOCKET_FD ,F_SETFL, flags | O_NONBLOCK);
	sleep(1);
#endif
}

static void socket_addr_prepare(struct sockaddr_in* addr)
{
	int len = 40;
	char str_ip[len];
	get_server_ip((char*)str_ip, len);

    PRINT_STATUS_ORDER_RESET();
    #ifdef _WIN_
        wchar_t* f1 = L"%S: %S\n";
        char buf[40];
        CharToOem(tr("Server's supposed IP"), buf);
        wprintf(f1, buf, str_ip);
    #else
        wchar_t* f1 = L"%s: %s\n";
        wprintf(f1, tr("Server's supposed IP"), str_ip);
    #endif

	(*addr).sin_family 		= AF_INET;
	(*addr).sin_addr.s_addr = inet_addr(str_ip);
	
	int portNo = atoi(GS_CONSOLE_PORT);
	
#if defined(_WIN_)
	if (WSAHtons(SOCKET_FD, (u_short)portNo, &((*addr).sin_port)) == SOCKET_ERROR) 
	{
		PRINT_STATUS_MSG_ERR(tr("WSAHtons call failed"));
	}
#else
	(*addr).sin_port = htons(portNo);
#endif
}

#define TMP_IP "tmp_ip"

static void get_server_ip(char* str_ip, int length)
{
#if defined(_WIN_)
    system("echo off && setlocal && (FOR /f \"delims=: tokens=1-2\" %C IN ('ipconfig /all ^| find \"IP-\"') DO ECHO %D>tmpip) && (FOR /F \"tokens=1\" %A IN (tmpip) DO ECHO %A>"TMP_IP") && endlocal && echo on && del tmpip");
#else
	system("ip -4 -o addr show dev eth0 | awk '{ gsub(/\\/[0-9]+$/, \"\", $4); print $4 }' > " TMP_IP);
#endif

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

#if defined(_WIN_)
	closesocket(SOCKET_FD);
	WSACleanup();
#else
	close(SOCKET_FD);
#endif
	
	SOCKET_FD = 0;

	PRINT_STATUS_DONE();
}

int get_gs_console_socket()
{
	return SOCKET_FD;
}

void console_line_rd(int fd, char* line, int size, int offset, RL_STAT* stat)
{
#if !defined(_WIN_)
    if (wait_rx(fd) < 0) return;
#endif

#if defined(_WIN_)
    sock_line_rd(fd, line, size, offset, stat);
#else
    line_rd(fd, line, size, offset, stat);
#endif
}

void console_line_wr(int fd, char* line, int size)
{	
#if !defined(_WIN_)
    wait_tx(fd);
#endif

#if defined(_WIN_)
    sock_line_wr(fd, line, size);
#else
    line_wr(fd, line, size);
#endif
}

static int wait_rx(int sock)
{
	FD_ZERO(&read_flags);
	FD_SET(sock, &read_flags);
    int result = select(sock+1, &read_flags, NULL, NULL, NULL );
	if ( result <= 0 ) return result;
	if ( FD_ISSET(sock, &read_flags) ) return 1;
	return 0;
}

static int wait_tx(int sock)
{
	FD_ZERO(&write_flags);
	FD_SET(sock, &write_flags);
    int result = select(sock+1, NULL, &write_flags, NULL, NULL );
	if ( result <= 0 ) return result;
	if ( FD_ISSET(sock, &write_flags) ) return 1;
	return 0;
}
