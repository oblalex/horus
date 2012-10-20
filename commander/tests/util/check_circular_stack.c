#include <check.h>
#include <stdlib.h>
#include <stdio.h>

#include "../../src/util/stack/circular_stack.h"

static CSTACK stack;

void printStack()
{
    printf("Printing stack: ");
    int* res;
    int i;
    for (i = 0; i < stack.capacity+1; ++i)
    {
        res = (int*)cstack_retrieve(&stack);
        if (res != NULL)
            printf("%2d ", (*res));
    }
    printf("\n");
}

START_TEST (test_circular_stack)
{
    cstack_init(&stack, 3, sizeof(int));

    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;
    int e = 5;

    cstack_push(&stack, ((void*)&a));
    cstack_push(&stack, ((void*)&b));
    cstack_push(&stack, ((void*)&c));

    printStack();

    cstack_push(&stack, ((void*)&d));
    cstack_push(&stack, ((void*)&e));

    printStack();

    cstack_push(&stack, ((void*)&a));
    cstack_push(&stack, ((void*)&a));
    cstack_push(&stack, ((void*)&a));

    printStack();

    cstack_push(&stack, ((void*)&a));
    cstack_push(&stack, ((void*)&b));
    cstack_push(&stack, ((void*)&c));
    cstack_push(&stack, ((void*)&d));
    cstack_push(&stack, ((void*)&e));

    printStack();

}
END_TEST

Suite* circular_stack_suite (void)
{
    Suite *s = suite_create ("Circular stack");

	TCase *tc_core = tcase_create ("Core");
    tcase_add_test (tc_core, test_circular_stack);
	suite_add_tcase (s, tc_core);

	return s;
}

int main (void)
{
	int number_failed;
    Suite *s = circular_stack_suite();
	SRunner *sr = srunner_create (s);
	srunner_run_all (sr, CK_NORMAL);
	number_failed = srunner_ntests_failed (sr);
	srunner_free (sr);
	return (number_failed == 0) ? EXIT_SUCCESS : EXIT_FAILURE;
}
