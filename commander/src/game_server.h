#ifndef GAME_SERVER_H
#define GAME_SERVER_H

#include <errno.h>
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <arpa/inet.h>

#include "print_status.h"
#include "ini_helper.h"

#define GS_PATH PATH_PARENT PATH_SEP

#define GS_LOGS_DIR "logs" PATH_SEP
#define GS_LOGS_DIR_PATH GS_PATH GS_LOGS_DIR

#define GS_LOG_STD "sconsole" FILE_EXT_LOG
#define GS_LOG_STD_PATH GS_LOGS_DIR_PATH GS_LOG_STD

#define GS_LOG_ERR "serrors" FILE_EXT_LOG
#define GS_LOG_ERR_PATH GS_LOGS_DIR_PATH GS_LOG_ERR

#define GS_LOG_EVT "sevents" FILE_EXT_LOG
#define GS_LOG_EVT_PATH_REL GS_LOGS_DIR GS_LOG_EVT
#define GS_LOG_EVT_PATH GS_PATH GS_LOG_EVT_PATH_REL

#define GS_NAME "il2server"
#define GS_EXE GS_NAME FILE_EXT_EXE
#define GS_EXE_PATH GS_PATH GS_EXE

#define GS_OUT_REDIRECT ">" GS_LOG_STD_PATH " 2>" GS_LOG_ERR_PATH
const char *const GS_START = "wine " GS_EXE_PATH GS_OUT_REDIRECT;

#define GS_CFG "confs" FILE_EXT_INI
const char *const GS_CFG_PATH = GS_PATH GS_CFG;

#define GS_CMDF_MAIN "server" FILE_EXT_CMD
const char *const GS_CMDF_MAIN_PATH = GS_PATH GS_CMDF_MAIN;

#define GS_CMDF_GC "gc" FILE_EXT_CMD
const char *const GS_CMDF_GC_PATH = GS_PATH GS_CMDF_GC;

const char *const GS_CONSOLE_PORT = "20001";
const char *const GS_CONSOLE_CLIENT_ADDR = "127.0.0.1";

#define GS_CMD_GC "GC"
#define GS_CMD_TIMEOUT(DELAY, CMD) "timeout " DELAY " " CMD
#define GS_CMD_RUN_FILE(FNAME) "f " FNAME
#define GS_CMD_EXIT "exit"

// configuration sections names
const char *const GS_CFG_CHAT	= "chat";
const char *const GS_CFG_GAME	= "game";
const char *const GS_CFG_CONSOLE = "Console";
const char *const GS_CFG_NET	= "NET";

void gs_start();
void gs_stop();
void gs_stop_log_processor();
void gs_exit_on_eror(char* err_msg);

void gs_init();
void gs_descriptor_init();
void gs_check_paths();
void gs_check_path_root();
void gs_check_path_logs();

void gs_check_settings();
void gs_scripts_generate();

void gs_config(INI_CONTAINER* cfg);
void gs_config_logging(INI_CONTAINER* cfg);
void gs_config_logging_chat(INI_CONTAINER* cfg);
void gs_config_logging_console(INI_CONTAINER* cfg);
void gs_config_logging_file(INI_CONTAINER* cfg);
void gs_config_console_connection(INI_CONTAINER* cfg);
void gs_config_version_checking(INI_CONTAINER* cfg);

void gs_scripts_generate_gc();
void gs_scripts_generate_main();

void gs_create_thread_and_wait_until_ready(pthread_t* thread, void*(*start_routine) (void*));

void* gs_keep_process_running();
void gs_create_process();
void gs_wait_loaded();
void gs_wait_process();

void* gs_process_console();
uint32_t gs_get_local_address();

void* gs_process_log();

void gs_cmd_exit();

typedef struct gs_descriptor
{
    pthread_t process_keeper;
    pthread_t log_processor;
    pthread_t console_processor;

    pid_t pid;

    int console_socket;

    BOOL run_process_keeper;
    BOOL run_log_processor;
    BOOL run_main;
    BOOL ready;
} GSIPTOR;

GSIPTOR GS;

void gs_start()
{
    if (GS.pid > (pid_t) 0)
    {
        return;
    }

    PRINT_STATUS_NEW("Starting game server");

    gs_init();

    gs_create_thread_and_wait_until_ready(&(GS.process_keeper), gs_keep_process_running);
    gs_create_thread_and_wait_until_ready(&(GS.console_processor), gs_process_console);

    PRINT_STATUS_DONE();
}

void gs_create_thread_and_wait_until_ready(pthread_t* thread, void*(*start_routine) (void*))
{
    GS.ready = FALSE;
    pthread_create(thread, NULL, start_routine, (void*) NULL);
    while(GS.ready == FALSE){
        waitMs(0, 100);
    }
}

void gs_stop()
{
    if (GS.pid <= (pid_t) 0)
    {
        return;
    }
    PRINT_STATUS_NEW("Stopping game server");

    GS.run_process_keeper = FALSE;

    if (GS.ready == TRUE){
        gs_cmd_exit();
        
        GS.run_main = FALSE;
        pthread_join(GS.console_processor, NULL);
    } else {
        kill(GS.pid, SIGSTOP);
    }

    pthread_join(GS.process_keeper, NULL);
    GS.pid = (pid_t) NULL;

    PRINT_STATUS_DONE();
}

void gs_stop_log_processor()
{
    GS.run_log_processor = FALSE;
    pthread_join(GS.log_processor, NULL);
}

void gs_init(){
    PRINT_STATUS_NEW("Initialization");

    gs_descriptor_init();
    gs_check_paths();
    gs_check_settings();
    gs_scripts_generate();

    PRINT_STATUS_DONE();
}

void gs_descriptor_init()
{
    GS.run_main = TRUE;
    GS.run_process_keeper = TRUE;
    GS.ready = FALSE;
    GS.run_log_processor = FALSE;

    GS.process_keeper = (pthread_t) NULL;
    GS.log_processor = (pthread_t) NULL;
    GS.console_processor = (pthread_t) NULL;

    GS.pid = (pid_t) NULL;
    GS.console_socket = -1;
}

void gs_check_paths()
{
    PRINT_STATUS_NEW("Checking server's paths");

    gs_check_path_root();
    gs_check_path_logs();

    PRINT_STATUS_DONE();
}

void gs_check_path_root()
{
    PRINT_STATUS_NEW("Checking root path");

    if (access(GS_EXE_PATH, X_OK) != -1) {
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_MSG_ERR("Please, make sure in following:");
        PRINT_STATUS_MSG_ERR("1. This program is located in IL-2 server's subdirectory.");
        PRINT_STATUS_MSG_ERR("2. You are located at that directory currently.");
        PRINT_STATUS_MSG_ERR("3. IL-2 server's executable file is named as \"" GS_EXE "\".");
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }
}

void gs_check_path_logs()
{
    PRINT_STATUS_NEW("Checking logs path");

    struct stat st;
    char* path = GS_LOGS_DIR_PATH;

    if ((stat(path, &st) != 0) && (errno == ENOENT))
    {
        PRINT_STATUS_MSG("Creating missing directory");
        if (mkdir(path, S_IRWXU) != 0)
        {
            char err_msg[sizeof(GS_LOGS_DIR_PATH)+20+1];
            strcpy (err_msg, "Failed to create \"");
            strcat (err_msg, path);
            strcat (err_msg, "\"");

            PRINT_STATUS_MSG_ERR(err_msg);
            PRINT_STATUS_FAIL();
            exit(EXIT_FAILURE);
        }
    }

    PRINT_STATUS_DONE();
}

void gs_check_settings()
{
    PRINT_STATUS_NEW("Checking server's configuration");

    PRINT_STATUS_MSG("Loading configuration");
    INI_CONTAINER* cfg = ini_start((char*)GS_CFG_PATH);

    const char* err_msg = NULL;

    while(1)
    {
        if (cfg == NULL) {
            err_msg = INI_MALLOC_ERR_CONTAINER;
            break;
        }
        if ((err_msg = cfg->error_msg) != NULL) break;

        gs_config(cfg);

        PRINT_STATUS_MSG("Saving configuration");
        ini_end(cfg);
        err_msg = cfg->error_msg;
        break;
    }

    if (err_msg == NULL) {
        PRINT_STATUS_DONE();
    } else {
        PRINT_STATUS_MSG_ERR(err_msg);
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }
}

void gs_config(INI_CONTAINER* cfg)
{
    gs_config_logging(cfg);
    gs_config_console_connection(cfg);
    gs_config_version_checking(cfg);
}

void gs_config_logging(INI_CONTAINER* cfg)
{
    gs_config_logging_chat(cfg);
    gs_config_logging_console(cfg);
    gs_config_logging_file(cfg);
}

void gs_config_logging_chat(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG("Suppressing logging to chat");
    ini_value_set(cfg, GS_CFG_CHAT, "autoLogDetail", "0");
}

void gs_config_logging_console(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG("Disabling console output saving to file");
    ini_value_set(cfg, GS_CFG_CONSOLE, "LOG", "0");
}

void gs_config_logging_file(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG("Setting logging output file");
    ini_value_set(cfg, GS_CFG_GAME, "eventlog", GS_LOG_EVT_PATH_REL);

    PRINT_STATUS_MSG("Enabling log resetting for every mission");
    ini_value_set(cfg, GS_CFG_GAME, "eventlogkeep", "0");

    PRINT_STATUS_MSG("Enabling buildings destruction logging");
    ini_value_set(cfg, GS_CFG_GAME, "eventlogHouse", "1");
}

void gs_config_console_connection(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG("Setting console port");
    ini_value_set(cfg, GS_CFG_CONSOLE, "IP", GS_CONSOLE_PORT);

    PRINT_STATUS_MSG("Setting console allowed client address");
    ini_value_set(cfg, GS_CFG_CONSOLE, "IPS", GS_CONSOLE_CLIENT_ADDR);
}

void gs_config_version_checking(INI_CONTAINER* cfg)
{
    PRINT_STATUS_MSG("Setting client version checking");
    ini_value_set(cfg, GS_CFG_NET, "checkRuntime", "1");
}

void gs_scripts_generate()
{
    PRINT_STATUS_NEW("Generating server's scripts");

    gs_scripts_generate_gc();
    gs_scripts_generate_main();

    PRINT_STATUS_DONE();
}

void gs_scripts_generate_gc()
{
    PRINT_STATUS_NEW("Generating garbage collecting script");

    FILE* f_gc = fopen (GS_CMDF_GC_PATH, "w");
    if (f_gc == NULL)
    {
        PRINT_STATUS_MSG_ERR("Unable to create file \"" GS_CMDF_GC "\"");
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }

    fputs(GS_CMD_GC "\n", f_gc);
    fputs(GS_CMD_GC "\n", f_gc);
    fputs(GS_CMD_GC "\n", f_gc);
    fputs(
        GS_CMD_TIMEOUT(
            "3600000",
            GS_CMD_RUN_FILE(GS_CMDF_GC)) "\n", f_gc);
    fclose (f_gc);
    PRINT_STATUS_DONE();
}

void gs_scripts_generate_main()
{
    PRINT_STATUS_NEW("Generating main script");

    FILE* f_sys = fopen (GS_CMDF_MAIN_PATH, "w");
    if (f_sys == NULL)
    {
        PRINT_STATUS_MSG_ERR("Unable to create file \"" GS_CMDF_MAIN "\"");
        PRINT_STATUS_FAIL();
        exit(EXIT_FAILURE);
    }

    fputs (GS_CMD_RUN_FILE(GS_CMDF_GC) "\n", f_sys);
    fclose (f_sys);

    PRINT_STATUS_DONE();
}

void* gs_keep_process_running()
{
    BOOL was_launched_earlier = FALSE;
    char* gs_is_down_msg = "Game server is shut down";

    while (1) {
        if (was_launched_earlier == TRUE)
        {
            PRINT_STATUS_MSG_NOIND("Restarting game server");
            waitMs(2, 0);
        }

        gs_create_process();
        if (GS.pid <= (pid_t) 0)
        {
            was_launched_earlier = TRUE;
            continue;
        }

        gs_wait_loaded();
        gs_wait_process();

        if (GS.run_process_keeper == TRUE)
        {
            PRINT_STATUS_MSG_ERR_NOIND(gs_is_down_msg);
            was_launched_earlier = TRUE;
        } else {
            PRINT_STATUS_MSG(gs_is_down_msg);
            break;
        }
    }

    return NULL;
}

void gs_create_process()
{
    PRINT_STATUS_NEW("Creating game server process");

    if ((GS.pid = fork()) < 0)
    {
        PRINT_STATUS_MSG_ERR("Failed to fork");
        PRINT_STATUS_FAIL();
        return;
    } else if (GS.pid == 0) {
        signal(SIGINT,	SIG_DFL);
        signal(SIGKILL,	SIG_DFL);
        signal(SIGTERM,	SIG_DFL);
        signal(SIGABRT,	SIG_DFL);
        signal(SIGCHLD, SIG_DFL);

        execl("/bin/sh", "sh", "-c", GS_START, (char*) 0);
        _exit(127);
    }

    PRINT_STATUS_DONE();
}

void gs_wait_loaded()
{
    PRINT_STATUS_NEW("Waiting for server is loaded");

    waitMs(1, 0);

    FILE* f_log = fopen (GS_LOG_STD_PATH, "rt");
    if (f_log == NULL)
    {
        PRINT_STATUS_MSG_ERR("Unable to open server's output file \"" GS_LOG_STD "\"");
        PRINT_STATUS_FAIL();
        gs_stop();
        exit(EXIT_FAILURE);
    }

    int line_len = 70;
    char line[line_len];

    while(GS.ready == FALSE){
        if (fgets(line, line_len, f_log) == NULL)
        {
            waitMs(0, 300);
        } else if (strstr(line, "1>") != NULL){
            GS.ready = TRUE;
        }
    }

    fclose (f_log);
    PRINT_STATUS_DONE();
}

void gs_wait_process()
{
    int childExitStatus;
    waitpid(GS.pid, &childExitStatus, 0);

    if(WIFEXITED(childExitStatus) == 0)
    {
        char err_msg [70];
        sprintf (err_msg, "waitpid() exited with an error. Status=%d", WEXITSTATUS(childExitStatus));
        PRINT_STATUS_MSG_ERR(err_msg);
    } else if (WIFSIGNALED(childExitStatus)) {
        char err_msg [60];
        sprintf (err_msg, "waitpid() exited due to a signal: %d", WTERMSIG(childExitStatus));
        PRINT_STATUS_MSG_ERR(err_msg);
    }

    GS.ready = FALSE;
}

void* gs_process_console()
{
    PRINT_STATUS_NEW("Waiting for server's console processor to start");

    if((GS.console_socket = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        gs_exit_on_eror("Console socket creation failed");
    }

	struct sockaddr_in addr;
    addr.sin_family = AF_INET;
    addr.sin_port = htons(atoi(GS_CONSOLE_PORT));
    addr.sin_addr.s_addr = htonl(gs_get_local_address());
    

	while(GS.ready != TRUE){    
    	if(connect(GS.console_socket, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    	{
        	gs_exit_on_eror("Console socket connection failed");
    	} else {
    		PRINT_STATUS_DONE();
    		GS.ready = TRUE;
    	}
	}
    
    while(GS.run_main == TRUE){
        waitMs(0, 100);
    }

    close(GS.console_socket);

    PRINT_STATUS_MSG("Console processor stopped");
    return NULL;
}

#define GS_SERVER_ADDR_FILE "server_addr"

uint32_t gs_get_local_address()
{
	system("lsof -i | grep -m 1 " GS_NAME " | awk '{print $9}' | awk -F: '{print $1}' > " GS_SERVER_ADDR_FILE);
	FILE* f_addr = fopen (GS_SERVER_ADDR_FILE, "rt");
	
	int line_len = 40;
    char line[line_len];
    fgets(line, line_len, f_addr);

	fclose (f_addr);
	system("rm " GS_SERVER_ADDR_FILE);

	int len = strlen(line);
	if( line[len-1] == '\n' )
		line[len-1] = 0;

	return inet_network((const char *)&line);
}

void* gs_process_log()
{
    PRINT_STATUS_NEW("Waiting for server's log processor to start");

    FILE* f_evt = fopen (GS_LOG_EVT_PATH, "rt");
    if (f_evt == NULL)
    {
        gs_exit_on_eror("Unable to open events log \"" GS_LOG_EVT "\"");
    }

    int line_len = 100;
    char line[line_len];

    while(fgets(line, line_len, f_evt) != NULL){}
    
    PRINT_STATUS_DONE();
	GS.ready = TRUE;

    while(GS.run_log_processor == TRUE){
        if (fgets(line, line_len, f_evt) == NULL)
        {
            waitMs(0, 300);
        } else {
            // TODO: parse event
            // detect if mission stopped
            printf(line);
        }
    }

    fclose (f_evt);
    PRINT_STATUS_MSG("Log processor stopped");

    return NULL;
}

void gs_exit_on_eror(char* err_msg)
{
	PRINT_STATUS_MSG_ERR(err_msg);
    PRINT_STATUS_FAIL();
    gs_stop();
    exit(EXIT_FAILURE);
}

void gs_cmd_exit()
{
	char* msg = GS_CMD_EXIT;
	send(GS.console_socket, msg, sizeof(msg), 0);
}

#endif // GAME_SERVER_H
