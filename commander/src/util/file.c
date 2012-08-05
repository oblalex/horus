#include "file.h"

#include <time.h>
#include <unistd.h>

void line_rd(int fd, char* line, int size, int offset, RL_STAT* stat)
{
	int line_len;
	int readcount;
	char c;

	(*stat).finished = FALSE;

	for ((*stat).length = 0; (*stat).length < size-offset-1; (*stat).length++)
	{
		readcount = read(fd, &c, 1);
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

void line_wr(int fd, char* line, int size)
{
	write(fd, line, size);
	write(fd, '\n', 1);
	usleep(10*1000);
}
