#include <check.h>
#include <stdlib.h>
#include <stdio.h>

#include "../../src/util/str.h"

START_TEST (test_str)
{
    char* src = "\\\\u0439\\\\u0446\\\\u0443\\\\u043a\\\\u0435\\\\u043d";
    char dst[255];

    printf("src:\t%s\n", src);
    str_rm_double_symb(src, dst, '\\');
    printf("result:\t%s\n", dst);
}

END_TEST

Suite* str_suite (void)
{
    Suite *s = suite_create ("Str tests");

    TCase *tc_core = tcase_create ("Core");
    tcase_add_test (tc_core, test_str);
    suite_add_tcase (s, tc_core);

    return s;
}

int main (void)
{
    int number_failed;
    Suite *s = str_suite();
    SRunner *sr = srunner_create (s);
    srunner_run_all (sr, CK_NORMAL);
    number_failed = srunner_ntests_failed (sr);
    srunner_free (sr);
    return (number_failed == 0) ? EXIT_SUCCESS : EXIT_FAILURE;
}
