#include <check.h>
#include <stdlib.h>
#include <stdio.h>

#include "../../src/util/circular_buffer.h"

static CBUFFER buff;

void printBuffer()
{
    printf("Printing buffer: ");
    int* res;
    int i;
    for (i = 0; i < buff.capacity+1; ++i)
    {
        res = (int*)cbuff_retrieve(&buff);
        if (res != NULL)
            printf("%2d ", (*res));
    }
    printf("\n");
}

START_TEST (test_circular_buffer)
{

    cbuff_init(&buff, 3, sizeof(int));

    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;
    int e = 5;

    cbuff_push(&buff, ((void*)&a));
    cbuff_push(&buff, ((void*)&b));
    cbuff_push(&buff, ((void*)&c));

    printBuffer();

    cbuff_push(&buff, ((void*)&d));
    cbuff_push(&buff, ((void*)&e));

    printBuffer();

    cbuff_push(&buff, ((void*)&a));
    cbuff_push(&buff, ((void*)&a));
    cbuff_push(&buff, ((void*)&a));

    printBuffer();

    cbuff_push(&buff, ((void*)&a));
    cbuff_push(&buff, ((void*)&b));
    cbuff_push(&buff, ((void*)&c));
    cbuff_push(&buff, ((void*)&d));
    cbuff_push(&buff, ((void*)&e));

    printBuffer();

}
END_TEST

Suite* circular_buffer_suite (void)
{
	Suite *s = suite_create ("Circular Buffer");

	TCase *tc_core = tcase_create ("Core");
	tcase_add_test (tc_core, test_circular_buffer);
	suite_add_tcase (s, tc_core);

	return s;
}

int main (void)
{
	int number_failed;
	Suite *s = circular_buffer_suite();
	SRunner *sr = srunner_create (s);
	srunner_run_all (sr, CK_NORMAL);
	number_failed = srunner_ntests_failed (sr);
	srunner_free (sr);
	return (number_failed == 0) ? EXIT_SUCCESS : EXIT_FAILURE;
}
