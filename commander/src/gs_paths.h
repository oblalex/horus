#ifndef GS_PATHS_H
#define PATH_GSS_H

#include "util/common.h"
#include "util/file_ext.h"

/** Path to game server directory */
#define PATH_GS 			PATH_PARENT PATH_SEP

/** Game server's logs subdirectory */
#define GS_LOGS_DIR 		"logs" PATH_SEP
#define PATH_GS_LOGS_DIR 	PATH_GS GS_LOGS_DIR

/** Game server's errors log file name */
#define GS_LOG_ERR_NAME		"error" FILE_EXT_LOG
#define PATH_GS_LOG_ERR 	PATH_GS_LOGS_DIR GS_LOG_ERR_NAME 

/** Game server's events log file name */
#define GS_LOG_EVT_NAME		"events" FILE_EXT_LOG
/** Path from server */
#define GS_LOG_EVT			GS_LOGS_DIR GS_LOG_EVT_NAME
#define PATH_GS_LOG_EVT		PATH_GS GS_LOG_EVT

/** Game server's output fifo name */
#define GS_STDOUT_NAME 		"stdout" FILE_EXT_LOG
#define PATH_GS_STDOUT		PATH_GS_LOGS_DIR GS_STDOUT_NAME

/** Game server's executable file name */
#define GS_EXE_NAME_SHORT	"il2server"
#define GS_EXE_NAME 		GS_EXE_NAME_SHORT FILE_EXT_EXE
#define PATH_GS_EXE			PATH_GS GS_EXE_NAME

/** Server's configuration file name */
#define GS_CFG_NAME 		"confs" FILE_EXT_INI
#define PATH_GS_CFG 		PATH_GS GS_CFG_NAME

/** Main server's command file name */
#define GS_CMDF_MAIN_NAME 	"server" FILE_EXT_CMD
#define PATH_GS_CMDF_MAIN 	PATH_GS GS_CMDF_MAIN_NAME

/** Garbage collecting server's command file name */
#define GS_CMDF_GC_NAME 	"gc" FILE_EXT_CMD
#define PATH_GS_CMDF_GC 	PATH_GS GS_CMDF_GC_NAME

void gs_check_path_root();
void gs_check_path_logs();

#endif // PATH_GSS_H

