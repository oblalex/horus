#include <stdlib.h>
#include <stdio.h>

#include "MinUnit.h"
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

char* test_circular_buffer()
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

    return 0;
}

static char* all_tests()
{
    mu_run_test(test_circular_buffer);
    return 0;
}

int main (void)
{
    return mu_run_tests(&all_tests, "Circular buffer");
}
