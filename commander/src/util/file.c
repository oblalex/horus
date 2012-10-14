#include "file.h"

#include <unistd.h>

void line_rd(int fd, char* line, int size, int offset, RL_STAT* stat)
{
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
	int done = 0;
	int tx;
	while (done<size)
	{
		tx = write(fd, line, size);
		if (tx <0 ) break;
		done += tx;
	}
}
