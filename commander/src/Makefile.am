# whatever flags you want to pass to the C compiler & linker
AM_CFLAGS = --pedantic -Wall -std=c11 -O2 -D_POSIX_C_SOURCE=199309L
AM_LDFLAGS = -lpthread

# this lists the binaries to produce, the (non-PHONY, binary) targets in
# the previous manual Makefile
bin_PROGRAMS = horusCommander
horusCommander_SOURCES = main.c
