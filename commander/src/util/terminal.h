/** 
 * @file
 * 		Contains terminal-style manipulating functions which allowing to set
 *		terminal's attributes, foreground and background
 */
#ifndef TERMINAL_H
#define TERMINAL_H

#include <sys/ioctl.h>

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

/** Teminal's current size container */
struct winsize TERM_SIZE;

/** 
 * Initialize terminal before applying styles:
 * @li Sets term_resizeHandler() as SIGWINCH handler.
 * @li Calls term_updateWindowSizeInfo().
 * @li Calls term_style_reset().
 */
void term_init();

/**
 * <a href="http://en.wikipedia.org/wiki/SIGWINCH">SIGWINCH</a> signal handler.
 * Calls term_updateWindowSizeInfo() when terminal size changes.
 */
void term_resizeHandler(int signum);

/** Load current terminal size info into #TERM_SIZE.*/
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

#endif // TERMINAL_H