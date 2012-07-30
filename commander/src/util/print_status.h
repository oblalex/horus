#ifndef PRINT_STATUS_H
#define PRINT_STATUS_H

#include "common.h"
#include "terminal.h"
#include "stack/stack_str.h"

#define STATUS_MSG_HEAD "::"

#define STATUS_MSG_BUSY "BUSY"
#define STATUS_MSG_DONE "DONE"
#define STATUS_MSG_FAIL "FAIL"

#define STATUS_INDENT_PRIME(SUB) (statuses.size-SUB)*3
#define STATUS_INDENT(SUB) 2+STATUS_INDENT_PRIME(SUB)

#define STATUS_HEAD_INDENT_PRIME STATUS_INDENT_PRIME(0)
#define STATUS_HEAD_INDENT STATUS_INDENT(0)

#define STATUS_MSG_INDENT STATUS_INDENT(1)+1

void print_status_tail(int color, const char* msg);
void print_status_raw(char* str, int color, const char* msg);
void PRINT_STATUS_NEW(char* str);

void print_status_finished(int color, const char* msg);
#define PRINT_STATUS_DONE() print_status_finished(TC_GREEN, STATUS_MSG_DONE);
#define PRINT_STATUS_FAIL() print_status_finished(TC_RED,   STATUS_MSG_FAIL);

void print_status_msg(int color, const char* str, BOOL do_indent);
#define PRINT_STATUS_MSG(STR)     print_status_msg(TC_CYAN, STR, TRUE);
#define PRINT_STATUS_MSG_ERR(STR) print_status_msg(TC_RED,  STR, TRUE);

#define PRINT_STATUS_MSG_NOIND(STR)     print_status_msg(TC_CYAN, STR, FALSE);
#define PRINT_STATUS_MSG_ERR_NOIND(STR) print_status_msg(TC_RED,  STR, FALSE);

void PRINT_STATUSES_RESET();

#endif // PRINT_STATUS_H