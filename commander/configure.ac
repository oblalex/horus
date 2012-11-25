#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ([2.68])
AC_INIT(
	[horus-commander],
	[0.4.1],
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

AC_PROG_CC

localedir='${prefix}/'$PACKAGE_NAME'/l10n'
AC_SUBST(localedir)

AM_CFLAGS='--pedantic -Wall -Wno-error=unused-result -Werror -Wfatal-errors -std=gnu99 -O2'
AC_SUBST(AM_CFLAGS)

AC_PROG_LIBTOOL
AM_PROG_CC_C_O

AC_CANONICAL_HOST

AC_MSG_CHECKING([for Windows OS])
case "$host_os" in
	mingw*)
		AH_TEMPLATE([_WIN_], Define this if you are under an Windows OS.)
		AC_DEFINE([_WIN_])
		win=true
		AC_MSG_RESULT(yes)
    ;;
    *)
		win=false
        AC_MSG_RESULT(no)
    ;;
esac

AM_CONDITIONAL(WIN, test x$win = xtrue)

AC_CONFIG_FILES([
	Makefile
	src/Makefile
	src/util/Makefile
	src/mxml/Makefile 
	src/domain/Makefile
	src/util/stack/Makefile
	tests/Makefile	
	tests/util/Makefile	
	l10n/Makefile.in
	src/HorusMLE/Makefile
	src/HorusConfig/Makefile
])

AC_OUTPUT
