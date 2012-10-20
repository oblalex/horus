#include "winsock_helper.h"

void sock_line_rd(SOCKET sock, char* line, int size, int offset, RL_STAT* stat)
{
    int readcount;
    unsigned char c;

    (*stat).finished = FALSE;

    for ((*stat).length = 0; (*stat).length < size-offset-1; (*stat).length++)
    {
        readcount = recv(sock, &c, 1, 0);
        if (readcount == 1)
        {
            *line = c;
            line++;
            if (c == '\n')
                break;
        } else return;
    }

    (*stat).finished = TRUE;
    *line=0;
}

void sock_line_wr(SOCKET sock, char* line, int size)
{
    int done = 0;
    int tx;
    while (done<size)
    {
        tx = send(sock, line, size, 0);
        if (tx <0 ) break;
        done += tx;
    }
}
