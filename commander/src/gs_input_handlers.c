#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>

#include "gs_input_handlers.h"
#include "gs.h"
#include "shell_parser.h"
#include "util/file.h"
#include "util/l10n.h"
#include "util/print_status.h"

static pthread_t h_gs_out, h_shell_in;

void input_handlers_start()
{
    PRINT_STATUS_NEW(tr("Starting console and shell handling threads"));

	pthread_create(&h_gs_out, NULL, &handle_gs_out, NULL);

	shell_parser_init();
	pthread_create(&h_shell_in, NULL, &handle_shell_in, NULL);
		
	PRINT_STATUS_DONE();
}

void* handle_gs_out()
{
	PRINT_STATUS_MSG_NOIND(tr("Game server's output processing started"));
	int GS_OUT_FD = 0; // get from socket
	handle_input(GS_OUT_FD, &gs_is_running, &foo_parse);
	return NULL;
}

void* handle_shell_in()
{
	PRINT_STATUS_MSG_NOIND(tr("User's shell activated"));
	handle_input(STDIN_FILENO, &gs_is_running, &shell_parse_string);
	return NULL;
}

void handle_input(int fd, BOOL (*run_condition)(), void (*parse)(char*))
{
    int line_len = 255;
    char line[line_len];
	int offset = 0;
	RL_STAT stat;

    while((*run_condition)() == TRUE)
	{
		line_rd(fd, line, line_len, offset, &stat);
        if (stat.finished == FALSE)
        {
			offset += stat.length;
            usleep(300*1000);
        } else {
			offset = 0;
			(*parse)(line);
		}
	}
}

void foo_parse(char* str)
{
	PRINT_STATUS_MSG(str);
}

void input_handlers_stop()
{
    PRINT_STATUS_NEW(tr("Stopping console and shell handling threads"));

	pthread_cancel(h_gs_out);
	PRINT_STATUS_MSG(tr("Game server's output processing finished"));

	pthread_cancel(h_shell_in);
	shell_parser_teardown();
	PRINT_STATUS_MSG(tr("User's shell deactivated"));

	PRINT_STATUS_DONE();
}
