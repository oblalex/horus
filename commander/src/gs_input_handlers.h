#ifndef GS_INPUT_HANDLERS_H
#define GS_INPUT_HANDLERS_H

#include "util/common.h"
#include "util/file.h"

void input_handlers_start();
void* handle_gs_out();
void* handle_shell_in();
void handle_input(int fd, BOOL (*run_condition)(), void (*read_fn)(int, char*, int, int, RL_STAT*), void (*parse)(char*));

void input_handlers_stop();

void foo_parse(char* str);

#endif // GS_INPUT_HANDLERS_H

