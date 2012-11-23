#ifndef MIN_UNIT_H
#define MIN_UNIT_H

#define mu_assert(message, test) do { if (!(test)) return message; } while (0)
#define mu_run_test(test) do { char *message = test(); tests_run++; if (message) return message; } while (0)

int mu_run_tests(char* (*tests_runner)(), const char *suite_name);

extern int tests_run;

#endif // MIN_UNIT_H
