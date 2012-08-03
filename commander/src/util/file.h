#ifndef GS_FILE_H
#define GS_FILE_H

#include "common.h"

typedef struct
{
	int length;
	BOOL finished;
} RL_STAT;

void line_rd(int fd, char* line, int size, int offset, RL_STAT* stat);
void line_wr(int fd, char* line, int size);

#endif // GS_FILE_H
