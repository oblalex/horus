TESTS = check_print_status \
    check_circular_buffer \
    check_circular_stack

check_PROGRAMS = check_print_status \
        check_circular_buffer \
        check_circular_stack

check_print_status_SOURCES = check_print_status.c
check_print_status_CFLAGS = @CHECK_CFLAGS@
check_print_status_LDADD = $(top_builddir)/src/util/libutil.la @CHECK_LIBS@

check_circular_buffer_SOURCES = check_circular_buffer.c
check_circular_buffer_CFLAGS = @CHECK_CFLAGS@
check_circular_buffer_LDADD = $(top_builddir)/src/util/libutil.la @CHECK_LIBS@

check_circular_stack_SOURCES = check_circular_stack.c
check_circular_stack_CFLAGS = @CHECK_CFLAGS@
check_circular_stack_LDADD = $(top_builddir)/src/util/stack/libstack.la @CHECK_LIBS@
