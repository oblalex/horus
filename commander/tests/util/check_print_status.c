#include <check.h>
#include <stdlib.h>
#include <wchar.h>

#include "../src/util/l10n.h"
#include "../src/util/print_status.h"
#include "../src/util/terminal.h"

START_TEST (test_print_status_done)
{
	term_init();

	PRINT_STATUS_NEW("Task 1");
	PRINT_STATUS_DONE();

	term_styleReset();
}
END_TEST

Suite* print_status_suite (void)
{
	Suite *s = suite_create ("Print Status");

	TCase *tc_core = tcase_create ("Core");
	tcase_add_test (tc_core, test_print_status_done);
	suite_add_tcase (s, tc_core);

	return s;
}

int main (void)
{
    locale_init("en");
	int number_failed;
	Suite *s = print_status_suite();
	SRunner *sr = srunner_create (s);
	srunner_run_all (sr, CK_NORMAL);
	number_failed = srunner_ntests_failed (sr);
	srunner_free (sr);
	return (number_failed == 0) ? EXIT_SUCCESS : EXIT_FAILURE;
}

