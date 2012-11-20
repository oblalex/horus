#include <check.h>
#include <stdlib.h>
#include <stdio.h>

#include "../../src/console_parser.h"
#include "check_regex.h"

START_TEST (test_console_parser)
{
    test_regex(CNSL_USER_JOIN,
               "[7:39:13 PM]	socket channel '1', ip 192.168.1.34:21000, =435SAP=Alex, is complete created",
               NULL);
    test_regex(CNSL_USER_LEFT,
               "[7:40:50 PM]	socketConnection with 192.168.1.34:21000 on channel 1 lost.  Reason: ",
               NULL);
}
END_TEST

Suite* console_parser_suite (void)
{
    Suite *s = suite_create ("Console parser");

    TCase *tc_core = tcase_create ("Core");
    tcase_add_test (tc_core, test_console_parser);
    suite_add_tcase (s, tc_core);

    return s;
}

int main (void)
{
    int number_failed;
    Suite *s = console_parser_suite();
    SRunner *sr = srunner_create (s);
    srunner_run_all (sr, CK_NORMAL);
    number_failed = srunner_ntests_failed (sr);
    srunner_free (sr);
    return (number_failed == 0) ? EXIT_SUCCESS : EXIT_FAILURE;
}