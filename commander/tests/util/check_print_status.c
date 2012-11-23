#include <stdlib.h>
#include <wchar.h>

#include "../src/util/l10n.h"
#include "../src/util/print_status.h"
#include "../src/util/terminal.h"
#include "MinUnit.h"

static char* test_print_status_done()
{
	term_init();

	PRINT_STATUS_NEW("Task 1");
	PRINT_STATUS_DONE();

    PRINT_STATUS_NEW("Task 2");
        PRINT_STATUS_NEW("Subtask 1");
            PRINT_STATUS_NEW("Subtask 1");
            PRINT_STATUS_MSG("Test message");
            PRINT_STATUS_MSG_NOIND("Test message with no indent");
            PRINT_STATUS_DONE();
        PRINT_STATUS_DONE();
        PRINT_STATUS_NEW("Subtask 2");
        PRINT_STATUS_DONE();
        PRINT_STATUS_NEW("Subtask 3");
        PRINT_STATUS_DONE();
    PRINT_STATUS_DONE();

    PRINT_STATUS_NEW("Task 3");
        PRINT_STATUS_NEW("Subtask 1");
        PRINT_STATUS_MSG_WRN("Test warning");
        PRINT_STATUS_MSG_WRN_NOIND("Test warning with no indent");
        PRINT_STATUS_DONE();
        PRINT_STATUS_NEW("Subtask 2");
        PRINT_STATUS_MSG_ERR("Test error");
        PRINT_STATUS_MSG_ERR_NOIND("Test error with no indent");
        PRINT_STATUS_FAIL();
    PRINT_STATUS_DONE();

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
