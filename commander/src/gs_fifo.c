#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>

#include "gs_fifo.h"
#include "gs_paths.h"
#include "util/file.h"
#include "util/print_status.h"

BOOL gs_fifos_create()
{
	PRINT_STATUS_NEW("Creating FIFOs");
	BOOL result;
	
	result = gs_fifo_create_in();
	
	if (result == TRUE)
		result = gs_fifo_create_out();

	if (result == TRUE){
		PRINT_STATUS_DONE();
	} else {
		PRINT_STATUS_FAIL();
	}
	return result;
}

BOOL gs_fifo_create_in()
{
	PRINT_STATUS_NEW("Creating game server stdin FIFO");
	return gs_fifo_create(PATH_GS_STDIN);
}

BOOL gs_fifo_create_out()
{
	PRINT_STATUS_NEW("Creating game server stdout FIFO");
	return gs_fifo_create(PATH_GS_STDOUT);
}

BOOL gs_fifo_create(char* path)
{
	if (mkfifo(path, S_IRWXU) == 0)
	{
		PRINT_STATUS_DONE();
		return TRUE;
	} 

	unlink(path);
	PRINT_STATUS_FAIL();		
	return FALSE;
}


BOOL gs_fifos_open(int* in, int* out)
{
	PRINT_STATUS_NEW("Opening FIFOs");
	
	gs_fifo_open_in(in);
	
	if (*in)
		gs_fifo_open_out(out);

	if (*in && *out){
		PRINT_STATUS_DONE();
		return TRUE;
	} else {
		PRINT_STATUS_FAIL();
		return FALSE;
	}
}

void gs_fifo_open_in(int* stream)
{
	PRINT_STATUS_NEW("Opening game server's stdin FIFO");
	gs_fifo_open(stream, PATH_GS_STDIN, O_WRONLY);
}

void gs_fifo_open_out(int* stream)
{
	PRINT_STATUS_NEW("Opening game server's stdout FIFO");
	gs_fifo_open(stream, PATH_GS_STDOUT, O_RDONLY | O_NONBLOCK);
}

void gs_fifo_open(int* stream, char* path, int access)
{
	if ((*stream = open(path, access)) < 0)
	{
		PRINT_STATUS_FAIL();
	} else {
		PRINT_STATUS_DONE();
	}
}

void gs_fifos_dispose(int* in, int* out)
{
 	PRINT_STATUS_NEW("Disposing FIFOs");

	gs_fifo_dispose_in(in);
	gs_fifo_dispose_out(out);

	PRINT_STATUS_DONE();
}

void gs_fifo_dispose_in(int* in)
{
 	PRINT_STATUS_MSG("Disposing input FIFO");
	gs_fifo_dispose(in, PATH_GS_STDIN);
}

void gs_fifo_dispose_out(int* out)
{
	PRINT_STATUS_MSG("Disposing output FIFO");
	gs_fifo_dispose(out, PATH_GS_STDOUT);
}

void gs_fifo_dispose(int* stream, char* path)
{
	if (*stream)
	{
		close(*stream);
		*stream = 0;
	}
	unlink(path);
}
