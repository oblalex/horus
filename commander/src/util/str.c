#include "str.h"
#include <string.h>
#include <stdio.h>

char* str_copy(const char* str)
{
    int n = strlen(str) + 1;
    char *dup = malloc(n);
    if (dup != NULL)
    {
        strcpy(dup, str);
    }
    return dup;
}

void str_null_termitate(char* str)
{
	int last_char_id = strlen(str)-1;
	if (str[last_char_id]=='\n') str[last_char_id] = 0;
}

void str_escape_unicode(char* src, int src_len, char* dst, int dst_len)
{
	int s_end = src+src_len;
	int d_end = dst+dst_len;

	int code_len;
	char code[code_len+1];
	memset(&code, '\0', code_len+1);

	while((src < s_end) && (dst < d_end))
	{
		if ((unsigned char)*src < 0x80){
			*(dst++) = *(src++);
		} else {
			if (dst >= (d_end-code_len-2)) break;
			*(dst++) = '\\';
			*(dst++) = 'u';
			sprintf(code, "%04x",					// from sequence
					(								// AAAxxxxx BByyyyyy
					 	((*(src) & 0x1F) << 6) +	// cut AAA by 0x1F
						((*(src+1)) & 0x3F)			// cut BB  by 0x3F
					 )								// and get xxxxxyyyyyy
					);
			src += 2;
			
			*(dst++) = *code;
			*(dst++) = *(code+1);
			*(dst++) = *(code+2);
			*(dst++) = *(code+3);
		}
	}
	*dst = '\0';
}
