:q#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ([2.68])
AC_INIT(
	[horus-commander],
	[0.0.50],
	https://github.com/oblalex/horus/issues,
	,
	http://oblalex.github.com/horus)

AC_CONFIG_AUX_DIR([config])
AC_CONFIG_MACRO_DIR([m4])

AM_GNU_GETTEXT([external])
AM_GNU_GETTEXT_VERSION([0.18.1])

AM_INIT_AUTOMAKE
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

localedir='${prefix}/'$PACKAGE_NAME'/l10n'
AC_SUBST(localedir)

AC_PROG_LIBTOOL
AM_PROG_CC_C_O

AC_CONFIG_FILES([
	Makefile
	src/Makefile
	src/util/Makefile	
	src/util/stack/Makefile
	tests/Makefile	
	tests/util/Makefile	
	l10n/Makefile.in
])

AC_OUTPUT
