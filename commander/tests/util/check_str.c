#include <stdlib.h>
#include <stdio.h>

#include "MinUnit.h"
#include "../../src/util/str.h"

static char* test_str_rm_double_symb()
{
    char* src = "\\\\u0439\\\\u0446\\\\u0443\\\\u043a\\\\u0435\\\\u043d";
    char dst[255];

    printf("src:\t%s\n", src);
    str_rm_double_symb(src, dst, '\\');
    printf("result:\t%s\n", dst);

    return 0;
}

static char* all_tests()
{
    mu_run_test(test_str_rm_double_symb);
    return 0;
}

int main (void)
{
    return mu_run_tests(&all_tests, "str");
}
