AM_LDFLAGS = -lpthread

bindir=$(prefix)/@PACKAGE@/
bin_PROGRAMS = horusCommander

horusCommander_SOURCES = main.c \
						main_parser.c \
						gs.c \
						gs_paths.c \
						gs_cfg.c \
						gs_scripts.c \
						gs_process.c \
						gs_console.c \
						gs_cmd.c \
						gs_input_handlers.c \
						shell_parser.c
						
horusCommander_LDADD = util/libutil.la mxml/libmxml.la

if WIN
horusCommander_LDADD += -lws2_32 -lpcre
endif

SUBDIRS = util mxml

