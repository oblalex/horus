#include <config.h>

#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <unistd.h>

#include "gs_input_handlers.h"
#include "gs.h"
#include "gs_console.h"
#include "shell_parser.h"
#include "console_parser.h"
#include "util/l10n.h"
#include "util/print_status.h"

static pthread_t h_gs_out, h_shell_in;

void input_handlers_start()
{
	BOOL shLock = TRUE;

    PRINT_STATUS_NEW(tr("Starting console and shell handling threads"), shLock);

	pthread_create(&h_gs_out, NULL, &handle_gs_out, NULL);

	shell_parser_init();
	pthread_create(&h_shell_in, NULL, &handle_shell_in, NULL);
		
	PRINT_STATUS_DONE(shLock);
}

void* handle_gs_out()
{
	BOOL shLock = TRUE;
	PRINT_STATUS_MSG_NOIND(tr("Game server's output processing started"), shLock);
    handle_input(get_gs_console_socket(), &gs_is_running, &console_line_rd, &console_parse_string);
    PRINT_STATUS_MSG(tr("Game server's output processing finished"), shLock);
	return NULL;
}

void* handle_shell_in()
{
	BOOL shLock = TRUE;
	PRINT_STATUS_MSG_NOIND(tr("User's shell activated"), shLock);
    shell_handle_in(&gs_is_running);
    PRINT_STATUS_MSG(tr("User's shell deactivated"), shLock);
	return NULL;
}

void handle_input(int fd, BOOL (*run_condition)(), void (*read_fn)(int, char*, int, int, RL_STAT*), void (*parse)(char*))
{
    int line_len = 1024;
    char line[line_len];
	int offset = 0;
	RL_STAT stat;

    while((*run_condition)() == TRUE)
	{
		(*read_fn)(fd, line, line_len, offset, &stat);
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

void input_handlers_stop()
{
	BOOL shLock = TRUE;
    PRINT_STATUS_NEW(tr("Stopping console and shell handling threads"), shLock);

    void* res;
    pthread_join(h_gs_out, &res);
    pthread_join(h_shell_in, &res);
	shell_parser_teardown();

	PRINT_STATUS_DONE(shLock);
}
