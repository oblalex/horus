#include <stdlib.h>
#include <stdio.h>

#include "../src/console_parser.h"
#include "util/check_regex.h"
#include "util/MinUnit.h"

char* test_console_parser()
{
    test_regex(CNSL_USER_JOIN,
               "socket channel '1', ip 192.168.1.34:21000, =435SAP=Alex, is complete created\\n",
               NULL);
    test_regex(CNSL_USER_LEFT,
               "socketConnection with 192.168.1.34:21000 on channel 1 lost.  Reason: \\n",
               NULL);
    test_regex(CNSL_CMD_MTL,
               "Chat: =435SAP=Alex: \\t`mtl\\n",
               NULL);

    return 0;
}

static char* all_tests()
{
    mu_run_test(test_console_parser);
    return 0;
}

int main (void)
{
    return mu_run_tests(&all_tests, "Console parser");
}
