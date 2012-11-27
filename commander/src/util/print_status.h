#ifndef PRINT_STATUS_H
#define PRINT_STATUS_H

#include <wchar.h>

#include "common.h"
#include "terminal.h"
#include "l10n.h"
#include "stack/stack_str.h"

#define STATUS_MSG_HEAD "::"

#define STATUS_MSG_BUSY tr("BUSY")
#define STATUS_MSG_DONE tr("DONE")
#define STATUS_MSG_FAIL tr("FAIL")

#define STATUS_INDENT_PRIME(SUB) (statuses.size-SUB)*3
#define STATUS_INDENT(SUB) 2+STATUS_INDENT_PRIME(SUB)

#define STATUS_HEAD_INDENT_PRIME STATUS_INDENT_PRIME(0)
#define STATUS_HEAD_INDENT STATUS_INDENT(0)

#define STATUS_MSG_INDENT STATUS_INDENT(1)+1

void print_status_tail(int color, const char* status);
void print_status_raw(char* str, int color, const char* status);
void PRINT_STATUS_NEW(char* str, BOOL shLock);

void print_status_finished(int color, const char* status, BOOL shUnlock);
void PRINT_STATUS_DONE(BOOL shUnlock);
void PRINT_STATUS_FAIL(BOOL shUnlock);

void print_status_msg(int color, char* str, BOOL do_indent, BOOL shLock);
void PRINT_STATUS_MSG(char* msg, BOOL shLock);
void PRINT_STATUS_MSG_WRN(char* msg, BOOL shLock);
void PRINT_STATUS_MSG_ERR(char* msg, BOOL shLock);

void PRINT_STATUS_MSG_NOIND(char* msg, BOOL shLock);
void PRINT_STATUS_MSG_WRN_NOIND(char* msg, BOOL shLock);
void PRINT_STATUS_MSG_ERR_NOIND(char* msg, BOOL shLock);

void PRINT_STATUS_ORDER_RESET();

void PRINT_STATUS_MULTI_START();
void PRINT_STATUS_MULTI_STOP();

void PRINT_STATUSES_RESET();

#endif // PRINT_STATUS_H
