#include "check_regex.h"
#include <stdarg.h>
#include <stdlib.h>
#include <stdio.h>
#include "../../src/util/regexxx.h"

static void test_one_regex(regex_t* regex, const char* sample);

void test_regex(const char* regex_text, const char * first, ...)
{
    printf("\nTesting regex '%s'\n\n", regex_text);

    regex_t RE;
    compile_regex(&RE, regex_text);

    const char * next = first;

    va_list lst;
    va_start(lst, first);

    while(next != NULL)
    {
        test_one_regex(&RE, next);
        next = va_arg(lst, const char*);
    }
    va_end(lst);

    regfree(&RE);

    printf("\n");
}

void test_one_regex(regex_t* regex, const char* sample)
{
    const char * p = sample;
    const int n_matches = 10;
    regmatch_t m[n_matches];

    while (1)
    {
        int i = 0;
        int nomatch = regexec (regex, p, n_matches, m, 0);
        if (nomatch)
        {
            printf ("No more matches.\n");
            return;
        }
        for (i = 0; i < n_matches; i++)
        {
            int start;
            int finish;
            if (m[i].rm_so == -1) {
                break;
            }
            start = m[i].rm_so + (p - sample);
            finish = m[i].rm_eo + (p - sample);
            if (i == 0)
            {
                printf ("$& is ");
            } else {
                printf ("$%d is ", i);
            }
            printf ("'%.*s' (bytes %d:%d)\n", (finish - start),
                    sample + start, start, finish);
        }
        p += m[0].rm_eo;
    }
}
