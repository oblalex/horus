#include "MinUnit.h"
#include <stdio.h>
#include <wchar.h>

int tests_run;

int mu_run_tests(char* (*tests_runner)(), const char* suite_name)
{
    tests_run = 0;

    char *result = tests_runner();

    if (result != 0) printf("%s\n", result);

    printf("Tests run: %d\n", tests_run);

    return result != 0;
}
