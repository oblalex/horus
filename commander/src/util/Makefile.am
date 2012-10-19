noinst_LTLIBRARIES = libutil.la

noinst_HEADERS = l10n.h
DEFS = -DLOCALEDIR=\"$(localedir)\" @DEFS@

libutil_la_SOURCES = str.c \
					ini.c \
					terminal.c \
					print_status.c \
					l10n.c \
					file.c \
          regexxx.c \
          timestamp_t.c \
          msg_queue.c \
          circular_buffer.c

if WIN
libutil_la_SOURCES += winproc.c winsock_helper.c
endif			
					
libutil_la_LIBADD = stack/libstack.la
libutil_la_LDFLAGS = $(LTLIBINTL)

SUBDIRS = stack
