#include "winstdin_helper.h"
#include "str.h"
#include <unistd.h>

void stdin_line_rd(int fd, char* line, int size, int offset, RL_STAT* stat)
{
    int readcount;
    unsigned char c;
    unsigned short int uc;

    (*stat).finished = FALSE;

    for ((*stat).length = 0; (*stat).length < size-offset-1; (*stat).length++)
    {
        readcount = read(fd, &c, 1);
        if (readcount == 1)
        {
            if (c >= 0x80)
            {
                uc = cp1251_to_utf8(c);

                *line = ((uc >> 8) & 0xFF);
                line++;
                *line = (uc & 0xFF);
                line++;

            } else {
                *line = c;
                line++;

                if (c == '\n')
                    break;
            }
        } else return;
    }

    (*stat).finished = TRUE;
    *line=0;
}
