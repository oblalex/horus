#ifndef CONSOLE_PARSER_H
#define CONSOLE_PARSER_H

#include "util/common.h"

/**
 *  Example: "[7:39:13 PM]	"
 */
#define CNSL_TIME                   "^\\[[[:digit:]]+:[[:digit:]]+:[[:digit:]]+[[:space:]]+[[:print:]]+\\][[:space:]]+"

/**
 *  Group 1: Channel number
 *  Group 2: IP without port
 *  Group 3: Callsign
 *
 *  Example: "[7:39:13 PM]	socket channel '1', ip 192.168.1.34:21000, =435SAP=Alex, is complete created"
 */
#define CNSL_USER_JOIN  CNSL_TIME   "socket channel '([[:digit:]]+)', ip ([[:print:]]+):[[:digit:]]+, ([[:print:]]+), is complete created"

/**
 *  Group 1: IP without port
 *  Group 2: Channel number
 *
 *  Example: "[7:40:50 PM]	socketConnection with 192.168.1.34:21000 on channel 1 lost.  Reason: "
 */
#define CNSL_USER_LEFT  CNSL_TIME   "socketConnection with ([[:print:]]+):[[:digit:]]+ on channel ([[:digit:]]+) lost.  Reason: "

void console_parse_string(char* str);

void console_parser_init();
void console_parser_teardown();

#endif // CONSOLE_PARSER_H
