#ifndef GS_SCRIPTS_H
#define GS_SCRIPTS_H

#include <stdio.h>

void gs_scripts_generate();
void gs_scripts_generate_gc();
void gs_scripts_generate_main();

static void check_script_file_created(FILE* fd, char* fname);

#endif // GS_SCRIPTS_H
