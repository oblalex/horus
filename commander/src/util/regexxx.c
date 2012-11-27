#include "regexxx.h"

#include "print_status.h"

BOOL compile_regex(regex_t* r, const char* regex_text)
{
	int status = regcomp (r, regex_text, REG_EXTENDED);
	
	if (status != 0) {
		int len = 255;
		char err_msg[len];
		regerror (status, r, err_msg, len);
		PRINT_STATUS_MSG_ERR(err_msg, FALSE);
		return FALSE;
	}

	return TRUE;
}
