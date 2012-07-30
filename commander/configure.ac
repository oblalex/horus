#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ([2.68])
AC_INIT([horus-commander], [0.0.1])

AC_CONFIG_AUX_DIR([config])
AC_CONFIG_MACRO_DIR([config/macro])

AM_INIT_AUTOMAKE([horus-commander], [0.0.1])
LT_INIT
AC_CONFIG_SRCDIR([src/main.c])
AC_CONFIG_HEADERS([config.h ])

# Checks for programs.
AC_PROG_CC

# Checks for libraries.
AC_PROG_LIBTOOL

# Checks for header files.

# Checks for typedefs, structures, and compiler characteristics.

# Checks for library functions.

AC_CONFIG_FILES([
	Makefile
	src/Makefile
	src/util/Makefile	
	src/util/stack/Makefile
])

AC_OUTPUT
