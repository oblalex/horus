#include "terminal.h"

#include <stdio.h>
#include <wchar.h>

#if defined(_WIN_)
	static HANDLE hStdin;
	static HANDLE hStdout;
	static DWORD fdwSaveOldMode;
	static HANDLE eventsThread;
#else
	#include <sys/ioctl.h>
	#include <signal.h>
	#include <unistd.h>
#endif

static void term_initResizeListener();
static void term_setWidth(int value);

static int TERM_WIDTH;

void term_init()
{
	term_initResizeListener();
	term_updateWindowSizeInfo();
	term_styleReset();
	
	#if defined(_WIN_)
	hStdout = GetStdHandle(STD_OUTPUT_HANDLE);
	#endif
}

void term_teardown()
{
	term_styleReset();
#if defined(_WIN_)
	if (eventsThread) {
        DWORD dwExitCode = 0;
		TerminateThread(eventsThread, dwExitCode);
	}
	SetConsoleMode(hStdin, fdwSaveOldMode);
#endif
}

#if defined(_WIN_)
static void term_initResizeListener()
{
    hStdin = GetStdHandle(STD_INPUT_HANDLE); 
	
    if (hStdin == INVALID_HANDLE_VALUE)
	{
		perror("GetStdHandle");
		exit(EXIT_FAILURE);
	}
 
	if (! GetConsoleMode(hStdin, &fdwSaveOldMode))
	{
		perror("GetConsoleMode");
		exit(EXIT_FAILURE);
	}

    if (! SetConsoleMode(hStdin, ENABLE_WINDOW_INPUT | ENABLE_MOUSE_INPUT) )
	{
		perror("SetConsoleMode");
		exit(EXIT_FAILURE);
	}
	
	eventsThread = CreateThread(NULL, 0, eventsThreadFunc, NULL, 0, NULL);
}

DWORD WINAPI eventsThreadFunc(void* data)
{
	DWORD cNumRead, i;
	int buf_len = 128;
    INPUT_RECORD irInBuf[buf_len]; 
	
	while (1) 
    { 
        if (! ReadConsoleInput( 
                hStdin,
                irInBuf,
                buf_len,
                &cNumRead) )
		{
			perror("ReadConsoleInput");
			exit(EXIT_FAILURE);
		}
 
        for (i = 0; i < cNumRead; i++) 
        {
            switch(irInBuf[i].EventType) 
            { 
                case WINDOW_BUFFER_SIZE_EVENT:
                    term_resizeHandler( irInBuf[i].Event.WindowBufferSizeEvent ); 
                    break; 

                default: 
                    break; 
            } 
        }
    } 
}

void term_resizeHandler(WINDOW_BUFFER_SIZE_RECORD wbsr)
{
	term_setWidth(wbsr.dwSize.X);
}

void term_updateWindowSizeInfo()
{
	CONSOLE_SCREEN_BUFFER_INFO csbi;
	int ret;
	ret = GetConsoleScreenBufferInfo(GetStdHandle( STD_OUTPUT_HANDLE ), &csbi);
	if(ret)
	{
		term_setWidth(csbi.dwSize.X);
	} else {
		term_setWidth(TERM_WIDTH_DEFAULT);
	}
}
#else
static void term_initResizeListener()
{
	signal(SIGWINCH, term_resizeHandler);
}

void term_resizeHandler(int signum)
{
	term_updateWindowSizeInfo();
}

void term_updateWindowSizeInfo()
{
	struct winsize term_size;
	ioctl(STDOUT_FILENO, TIOCGWINSZ, &term_size);
	term_setWidth(term_size.ws_col);
}
#endif

void term_style(int attr, int fg, int bg)
{
#if defined(_WIN_)
	if (fg == TC_NONE) fg = TC_WHITE;
	SetConsoleTextAttribute(hStdout, attr | fg | (bg << 4));
#else
	char command[13];
	sprintf(command, "%c[%d;%d;%dm", 0x1B, attr, fg + 30, bg + 40);
	wprintf(L"%s", command);
#endif
}

void term_styleReset()
{
	term_style(TA_NONE, TC_NONE, TC_NONE);
}

static void term_setWidth(int value)
{
	TERM_WIDTH = value;
}

int term_getWidth()
{
	return TERM_WIDTH;
}
