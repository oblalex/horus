#ifndef MAIN_PARSER_H
#define MAIN_PARSER_H

#include "util/common.h"

#define ARG_VERSION 		"--version"
#define ARG_VERSION_SHORT 	"-v"

#define ARG_HELP 			"--help"
#define ARG_HELP_SHORT 		"-h"

void parseArgs(int argc, char const *argv[]);

static BOOL recon_version(char const *arg);
static void print_version();

static BOOL recon_help(char const *arg);
static void print_help(char* exec_name);

static void non_recon(char const *arg);

#endif // MAIN_PARSER_H
