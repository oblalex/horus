#include <stdlib.h>
#include <wchar.h>

#include "../src/util/l10n.h"
#include "../src/util/print_status.h"
#include "../src/util/terminal.h"
#include "MinUnit.h"

static char* test_print_status_done()
{
	term_init();

    BOOL shLock = FALSE;

    PRINT_STATUS_NEW("Task 1", shLock);
    PRINT_STATUS_DONE(shLock);

    PRINT_STATUS_NEW("Task 2", shLock);
        PRINT_STATUS_NEW("Subtask 1", shLock);
            PRINT_STATUS_NEW("Subtask 1", shLock);
            PRINT_STATUS_MSG("Test message", shLock);
            PRINT_STATUS_MSG_NOIND("Test message with no indent", shLock);
            PRINT_STATUS_DONE(shLock);
        PRINT_STATUS_DONE(shLock);
        PRINT_STATUS_NEW("Subtask 2", shLock);
        PRINT_STATUS_DONE(shLock);
        PRINT_STATUS_NEW("Subtask 3", shLock);
        PRINT_STATUS_DONE(shLock);
    PRINT_STATUS_DONE(shLock);

    PRINT_STATUS_NEW("Task 3", shLock);
        PRINT_STATUS_NEW("Subtask 1", shLock);
        PRINT_STATUS_MSG_WRN("Test warning", shLock);
        PRINT_STATUS_MSG_WRN_NOIND("Test warning with no indent", shLock);
        PRINT_STATUS_DONE(shLock);
        PRINT_STATUS_NEW("Subtask 2", shLock);
        PRINT_STATUS_MSG_ERR("Test error", shLock);
        PRINT_STATUS_MSG_ERR_NOIND("Test error with no indent", shLock);
        PRINT_STATUS_FAIL(shLock);
    PRINT_STATUS_DONE(shLock);

	term_styleReset();

    return 0;
}

static char* all_tests()
{
     mu_run_test(test_print_status_done);
     return 0;
}

int main (void)
{
    locale_init("en");
    return mu_run_tests(&all_tests, "Print status");
}
