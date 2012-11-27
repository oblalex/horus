#ifndef SHELL_PARSER_H
#define SHELL_PARSER_H

#include "util/common.h"

typedef struct
{
    char* cmd;
    int ln;
} SH_CMD_RAW;

#define SH_CMD_COUNT            16

#define SH_PROMPT               "> "
#define SH_EXIT                 "exit"

#define SH_CHAT_ALL_RAW         "chall"
#define SH_CHAT_USER_RAW        "chusr"
#define SH_CHAT_ARMY_RAW        "charm"

#define SH_CHAT_ALL             "^" SH_CHAT_ALL_RAW  "[[:space:]]+([[:print:]]+)"
#define SH_CHAT_USER            "^" SH_CHAT_USER_RAW "[[:space:]]+([[:print:]]+)[[:space:]]+([[:print:]]+)"

#define SH_CHAT_ARMY_R          "red"
#define SH_CHAT_ARMY_B          "blue"
#define SH_CHAT_ARMY_N          "none"

#define SH_CHAT_ARMY            "^" SH_CHAT_ARMY_RAW "[[:space:]]+(" \
                                SH_CHAT_ARMY_R "|" \
                                SH_CHAT_ARMY_B "|" \
                                SH_CHAT_ARMY_N "|r|b|n|1|2|0)[[:space:]]+([[:print:]]+)"

#define SH_MSSN_LOAD            "mload"
#define SH_MSSN_UNLOAD          "muload"
#define SH_MSSN_RUN             "mrun"
#define SH_MSSN_RERUN           "mrerun"
#define SH_MSSN_END             "mend"
#define SH_MSSN_START           "mstart"
#define SH_MSSN_RESTART         "mrestart"
#define SH_MSSN_STOP            "mstop"
#define SH_MSSN_NEXT            "mnext"
#define SH_MSSN_PREV            "mprev"
#define SH_MSSN_TIME_LEFT       "mtl"
#define SH_MSSN_TIME_LEFT_SET   "^mtl[[:space:]]+set[[:space:]]+([[:digit:]]+):([[:digit:]]+):([[:digit:]]+)"

void shell_parser_init();
void shell_parser_teardown();

void shell_handle_in(BOOL (*run_condition)());
void shell_parse_string(char* str);

void shell_lock();
void shell_unlock();

#endif // SHELL_PARSER_H
