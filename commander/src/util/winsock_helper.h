#ifndef WIN_SOCK_HELPER_H
#define WIN_SOCK_HELPER_H

#include "file.h"
#include <winsock2.h>

void sock_line_rd(SOCKET sock, char* line, int size, int offset, RL_STAT* stat);
void sock_line_wr(SOCKET sock, char* line, int size);

#endif // WINSOCK_HELPER_H
