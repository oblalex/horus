#ifndef GS_FILE_H
#define GS_FILE_H

#include <config.h>
#include "common.h"

typedef struct
{
	int length;
	BOOL finished;
} RL_STAT;

#if defined(_WIN_)
    #include <windows.h>
    void hnd_line_rd(HANDLE hFile, char* line, int size, int offset, RL_STAT* stat);
#endif

void line_rd(int fd, char* line, int size, int offset, RL_STAT* stat);
void line_wr(int fd, char* line, int size);

#endif // GS_FILE_H
