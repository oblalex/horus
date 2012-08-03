AM_LDFLAGS = -lpthread

bindir=$(prefix)/@PACKAGE@/
bin_PROGRAMS = horusCommander

horusCommander_SOURCES = main.c \
						gs.c \
						gs_paths.c \
						gs_cfg.c \
						gs_scripts.c \
						gs_fifo.c \
						gs_cmd.c
horusCommander_LDADD = util/libutil.la

SUBDIRS = util

