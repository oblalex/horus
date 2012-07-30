/** 
 * @file
 * 		Contains string-manipulating functions which are not a part of the
 *		standard functions in <string.h>.
 */
#ifndef STR_H
#define STR_H

/** 
 * Create a copy of string.
 *
 * @param str
 *		Pointer to the source string.
 * @return
 *		Pointer to a new string or NULL.
 *
 * @warning
 *		Free memory after using by calling free() from <stdlib.h>.
 */
char* str_copy(const char* str);

#endif // STR_H