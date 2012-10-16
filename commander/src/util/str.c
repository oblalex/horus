#include "str.h"
#include <string.h>
#include <stdio.h>
#include <wchar.h>

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

int str_copy_symbols(char* src, int src_len, int max_count, int offset, char* dst, int dst_len)
{
	int s_end = src+src_len;
	int d_end = dst+dst_len;

	char* start = src;
	src += offset;

	int count = 0;

	while((src < s_end) && (dst < d_end) && (count < max_count))
	{
		if ((unsigned char)*src < 0x80)
		{
			*(dst++) = *(src++);
		} else if ((src < s_end-1) && (dst < d_end-1) && (count < max_count))
		{
			*(dst++) = *(src++);
			*(dst++) = *(src++);
		} else {
			break;
		}
		count++;
	}

	return (int) (src - start);
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
					 	((*(src) & 0x1F) << 6) |	// cut AAA by 0x1F
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

char* substring(int start, int stop, char* src, char* dst, int size)
{
	int count = stop - start;
	if ( count >= --size )
	{
		count = size;
	}
	sprintf(dst, "%.*s", count, src + start);
	return dst;
}

