#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ([2.68])
AC_INIT([horus-commander], [0.0.1])

AC_CONFIG_AUX_DIR([config])
AC_CONFIG_MACRO_DIR([m4])
AM_GNU_GETTEXT([external])

AM_INIT_AUTOMAKE([horus-commander], [0.0.1])
LT_INIT
AC_CONFIG_SRCDIR([src/main.c])
AC_CONFIG_HEADERS([config.h ])

PKG_CHECK_MODULES(
	[CHECK],
	[check >= 0.9.4],
	[have_check="yes"],
	[
	 	[have_check="no"],
		AC_MSG_WARN([Check not found; cannot run unit tests!])
	]
)

AC_PROG_CC
AC_PROG_LIBTOOL
AM_PROG_CC_C_O

AC_CONFIG_FILES([
	l10n/Makefile.in
	Makefile
	src/Makefile
	src/util/Makefile	
	src/util/stack/Makefile
	tests/Makefile	
	tests/util/Makefile	
])

AC_OUTPUT
