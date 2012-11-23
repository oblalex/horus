#include <stdlib.h>
#include <stdio.h>

#include "MinUnit.h"
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

char* test_circular_stack()
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

    return 0;
}

static char* all_tests()
{
    mu_run_test(test_circular_stack);
    return 0;
}

int main (void)
{
    return mu_run_tests(&all_tests, "Circular stack");
}
