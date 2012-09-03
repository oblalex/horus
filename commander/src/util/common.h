/**
 * @file
 * 		Common types and definitions.
 */
#ifndef COMMON_H
#define COMMON_H

#include <config.h>

#if defined(_WIN_)
	#include <windows.h>
#else
	typedef enum
	{
		FALSE, TRUE
	} BOOL;
#endif 

#define PATH_PARENT	".."

#if defined(_WIN_)
	#define PATH_SEP 	"\\"
	#define DEV_NULL 	"NUL"
#else
	#define PATH_SEP 	"/"
	#define DEV_NULL 	"/dev/null"
#endif 

#define NEW_LINE	"\n"
#define CH_NULL		"\0"

#endif // COMMON_H
