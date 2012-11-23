noinst_LTLIBRARIES = libchutil.la
libchutil_la_SOURCES = MinUnit.c check_regex.c
libchutil_la_LIBADD = $(top_builddir)/src/util/libutil.la

TESTS = check_print_status \
    check_circular_buffer \
    check_circular_stack \
    check_str

check_PROGRAMS = check_print_status \
        check_circular_buffer \
        check_circular_stack \
        check_str

check_print_status_SOURCES = check_print_status.c
check_print_status_LDADD = $(top_builddir)/src/util/libutil.la libchutil.la

if WIN
check_print_status_LDADD += -lpthread
endif

check_circular_buffer_SOURCES = check_circular_buffer.c
check_circular_buffer_LDADD = $(top_builddir)/src/util/libutil.la libchutil.la

check_circular_stack_SOURCES = check_circular_stack.c
check_circular_stack_LDADD = $(top_builddir)/src/util/stack/libstack.la libchutil.la

check_str_SOURCES = check_str.c
check_str_LDADD = $(top_builddir)/src/util/libutil.la libchutil.la
