/** 
 * @file
 * 		Contains terminal-style manipulating functions which allowing to set
 *		terminal's attributes, foreground and background
 */
#ifndef TERMINAL_H
#define TERMINAL_H

#include <config.h>

#if defined(_WIN_)
	#include <windows.h>
#endif

/**
 * @defgroup TERMINAL_COLORS Terminal colors
 * @{
 */
#define TC_BLACK	0
#define TC_RED		1
#define TC_GREEN	2
#define TC_YELLOW	3
#define TC_BLUE		4
#define TC_MAGENTA	5
#define TC_CYAN		6
#define TC_WHITE	7
#define TC_NONE		8
/**@}*/

/**
 * @defgroup TERMINAL_ATTRIBUTES Terminal attributes
 * @{
 */
#define TA_NONE			0
#define TA_BRIGHT 		1
#define TA_DIM			2
#define TA_UNDERLINE 	3
#define TA_BLINK		4
#define TA_REVERSE		7
#define TA_HIDDEN		8
/**@}*/

#define TERM_WIDTH_DEFAULT 80

void term_init();
static void term_initResizeListener();

#if defined(_WIN_)
DWORD WINAPI eventsThreadFunc(void* data);
void term_resizeHandler(WINDOW_BUFFER_SIZE_RECORD wbsr);
#else
void term_resizeHandler(int signum);
#endif

void term_updateWindowSizeInfo();

/**
 * Set current terminal style.
 *
 * @param attr
 *		Text @link TERMINAL_ATTRIBUTES attribute@endlink.
 * @param fg
 *		Foreground @link TERMINAL_COLORS color@endlink.
 * @param bg
 *		Background @link TERMINAL_COLORS color@endlink.
 */
void term_style(int attr, int fg, int bg);

/** 
 * Reset terminal style: set #TC_NONE as foreground and background colors
 * and #TA_NONE as attribute.
 */
void term_styleReset();

static void term_setWidth(int value);
int term_getWidth();

#endif // TERMINAL_H