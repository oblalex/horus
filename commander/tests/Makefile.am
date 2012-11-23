TESTS = check_console_parser

check_PROGRAMS = check_console_parser

check_console_parser_SOURCES = check_console_parser.c
check_console_parser_LDADD = $(top_builddir)/src/util/libutil.la util/libchutil.la

if WIN
check_console_parser_LDADD += -lpthread -lpcre
endif

SUBDIRS = util

