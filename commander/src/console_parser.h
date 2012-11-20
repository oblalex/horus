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
 *  Example: "socket channel '1', ip 192.168.1.34:21000, =435SAP=Alex, is complete created\n"
 */
#define CNSL_USER_JOIN  "socket channel '([[:digit:]]+)', ip ([[:print:]]+):[[:digit:]]+, ([[:print:]]+), is complete created\\\\n"

/**
 *  Group 1: IP without port
 *  Group 2: Channel number
 *
 *  Example: "socketConnection with 192.168.1.34:21000 on channel 1 lost.  Reason: \n"
 */
#define CNSL_USER_LEFT  "socketConnection with [[:print:]]+:[[:digit:]]+ on channel ([[:digit:]]+) lost.  Reason: \\\\n"

void console_parse_string(char* str);

void console_parser_init();
void console_parser_teardown();

#endif // CONSOLE_PARSER_H
