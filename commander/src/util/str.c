#include "str.h"
#include <string.h>
#include <stdio.h>
#include <wchar.h>

#if defined(_WIN_)
    #include <windows.h>
#endif

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
    int last_char_id = strlen(str);
    if ((str[last_char_id]==13) || (str[last_char_id]==10))
        str[last_char_id] = '\0';
    if (str[last_char_id-1]==10)
        str[last_char_id-1] = '\0';
}

int str_copy_symbols(char* src, int src_len, int max_count, int offset, char* dst, int dst_len)
{
    char* s_end = (char*)((*src)+src_len);
    char* d_end = (char*)((*dst)+dst_len);

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
    char* s_end = (char*)((*src)+src_len);
    char* d_end = (char*)((*dst)+dst_len);

    int code_len = 4;
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

static unsigned short int cp1251_to_utf8_table[128] = {
    0x0402,0x0403,0x201A,0x0453,0x201E,0x2026,0x2020,0x2021,0x20AC,0x2030,0x0409,0x2039,0x040A,0x040C,0x040B,0x040F,
    0x0452,0x2018,0x2019,0x201C,0x201D,0x2022,0x2013,0x2014,0x0,0x2122,0x0459,0x203A,0x045A,0x045C,0x045B,0x045F,
    0x00A0,0x040E,0x045E,0x0408,0x00A4,0x0490,0x00A6,0x00A7,0x0401,0x00A9,0x0404,0x00AB,0x00AC,0x00AD,0x00AE,0x0407,
    0x00B0,0x00B1,0x0406,0x0456,0x0491,0x00B5,0x00B6,0x00B7,0x0451,0x2116,0x0454,0x00BB,0x0458,0x0405,0x0455,0x0457,
    0x0410,0x0411,0x0412,0x0413,0x0414,0x0415,0x0416,0x0417,0x0418,0x0419,0x041A,0x041B,0x041C,0x041D,0x041E,0x041F,
    0x0420,0x0421,0x0422,0x0423,0x0424,0x0425,0x0426,0x0427,0x0428,0x0429,0x042A,0x042B,0x042C,0x042D,0x042E,0x042F,
    0x0430,0x0431,0x0432,0x0433,0x0434,0x0435,0x0436,0x0437,0x0438,0x0439,0x043A,0x043B,0x043C,0x043D,0x043E,0x043F,
    0x0440,0x0441,0x0442,0x0443,0x0444,0x0445,0x0446,0x0447,0x0448,0x0449,0x044A,0x044B,0x044C,0x044D,0x044E,0x044F};

unsigned short int cp1251_to_utf8(unsigned char in)
{
    unsigned short int out = 0;

    if (in < 0x80)
    {
        out += in;
    } else {
        unsigned short int tmp = cp1251_to_utf8_table[in-0x80];
        out = ((((tmp >> 6) & 0x1F) | 0xC0) << 8) | ((tmp & 0x3F) | 0x80);
    }

    return out;
}

#ifdef _WIN_
int utf8_to_cp1251(char *src, char *dst)
{
    int result_u, result_c;

    result_u = MultiByteToWideChar(CP_UTF8,
                                   0,
                                   src,
                                   -1,
                                   0,
                                   0);

    if (!result_u)
        return 0;

    wchar_t *ures = (wchar_t*)malloc(sizeof(wchar_t)*result_u);

    if(!MultiByteToWideChar(CP_UTF8,
                            0,
                            src,
                            -1,
                            ures,
                            result_u))
    {
        free(ures);
        return 0;
    }

    result_c = WideCharToMultiByte(
                1251,
                0,
                ures,
                -1,
                0,
                0,
                0, 0);

    if(!result_c)
        return 0;

    char *cres = (char*)malloc(sizeof(char)*result_c);

    if(!WideCharToMultiByte(
                1251,
                0,
                ures,
                -1,
                cres,
                result_c,
                0, 0))
    {
        free(cres);
        return 0;
    }

    free(ures);
    memcpy(dst, cres, result_c);
    free(cres);
    return result_c;
}
#endif
