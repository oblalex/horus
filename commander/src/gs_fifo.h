#ifndef GS_FIFO_H
#define GS_FIFO_H

#include <stdio.h>
#include "util/common.h"

BOOL gs_fifos_create();
BOOL gs_fifo_create_in();
BOOL gs_fifo_create_out();
BOOL gs_fifo_create(char* path);

BOOL gs_fifos_open(int* in, int* out);
void gs_fifo_open_in(int* in);
void gs_fifo_open_out(int* out);
void gs_fifo_open(int* stream, char* path, int access);

void gs_fifos_dispose(int* in, int* out);
void gs_fifo_dispose_in(int* stream);
void gs_fifo_dispose_out(int* stream);
void gs_fifo_dispose(int* stream, char* path);

#endif // GS_FIFO_H
