#include "pilot_manager.h"
#include "gs_cmd.h"
#include "gs_mission_manager.h"
#include "domain/d_army.h"
#include "util/print_status.h"
#include "util/msg_queue.h"
#include "util/l10n.h"
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

static void pm_user_join(D_PILOT_ELEM* pe);
static void pm_user_left(uint2 channel);
static void pm_mtl(char* callsign);

static void msg_delete(MSG_T* msg);
static void msg_enqueue(MSG_T* msg);

static void* pm_msg_dispatcher();

static void pm_lst_clear();

static MSG_QUEUE        MSG_Q;
static pthread_cond_t   MSG_CND = PTHREAD_COND_INITIALIZER;
static pthread_mutex_t  MSG_MTX = PTHREAD_MUTEX_INITIALIZER;

static pthread_t H_MSG_DISPATCHER;
static BOOL DO_WORK = FALSE;

static D_PILOT_ELEM*    FIRST;
static D_PILOT_ELEM*    LAST;
static uint2            COUNT;

void pm_init()
{
    PRINT_STATUS_NEW(tr("Pilot manager initialization"));

    DO_WORK = TRUE;

    FIRST = NULL;
    LAST = NULL;
    COUNT = 0;

    pthread_create(&H_MSG_DISPATCHER, NULL, &pm_msg_dispatcher, NULL);

    PRINT_STATUS_DONE();
}

void pm_teardown()
{
    PRINT_STATUS_NEW(tr("Pilot manager tearing down"));

    DO_WORK = FALSE;

    pthread_cond_signal(&MSG_CND);
    pm_lst_clear();

    PRINT_STATUS_DONE();
}

void pm_lst_clear()
{
    D_PILOT_ELEM* curr;

    for (curr = FIRST; curr != NULL; curr = curr->next)
        pm_elem_free(&curr);

    FIRST = NULL;
    LAST = NULL;
    COUNT = 0;
}

void pm_elem_init(D_PILOT_ELEM** _this, char* callsign, char* ip, uint2 channel)
{
    if ((*_this) != NULL) return;

    (*_this) = (D_PILOT_ELEM*)malloc(sizeof(D_PILOT_ELEM));

    (*_this)->data      = NULL;
    d_pilot_init(&((*_this)->data), callsign);

    (*_this)->IP        = (char*)malloc(sizeof(char)*strlen(ip)+1);
    strcpy((*_this)->IP, ip);

    (*_this)->channel   = channel;
    (*_this)->side      = ARMY_NONE;
    (*_this)->next      = NULL;
}

void pm_elem_free(D_PILOT_ELEM** _this)
{
    if ((*_this) == NULL) return;

    d_pilot_free(&((*_this)->data));
    free((*_this)->IP);
    free(*_this);
}

void pm_user_join_req(char* callsign, char* ip, uint2 channel)
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = PM_REQ_USR_JOIN;

    D_PILOT_ELEM* pe = NULL;
    pm_elem_init(&pe, callsign, ip, channel);

    msg->data = (void*)pe;
    msg_enqueue(msg);
}

void pm_user_left_req(uint2 channel)
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = PM_REQ_USR_LEFT;

    uint2* chnl = (uint2*)malloc(sizeof(uint2));
    *chnl = channel;

    msg->data = (void*)chnl;
    msg_enqueue(msg);
}

void pm_mtl_req(char* callsign)
{
    MSG_T* msg = (MSG_T*)malloc(sizeof(MSG_T));
    msg->type = PM_REQ_MTL;

    char* data = (char*)malloc(strlen(callsign)+1);
    strcpy(data, callsign);

    msg->data = (void*)data;
    msg_enqueue(msg);
}

void pm_user_join(D_PILOT_ELEM* pe)
{
    if (FIRST == NULL) FIRST = pe;
    if (LAST  != NULL) LAST->next = pe;

    LAST = pe;

    COUNT++;

    char msg[70];
    sprintf(msg, tr("%s (%s) joined."), pe->data->callsign, pe->IP);
    PRINT_STATUS_MSG_NOIND(msg);

    gs_cmd_greet_user(pe->data->callsign);
    pm_mtl(pe->data->callsign);
}

void pm_user_left(uint2 channel)
{
    D_PILOT_ELEM* curr;
    D_PILOT_ELEM* prev = NULL;
    BOOL FOUND = FALSE;

    for (curr = FIRST;
         curr != NULL;
         prev = curr, curr = curr->next)
    {
        if (curr->channel == channel)
        {
            FOUND = TRUE;
            break;
        }
        prev = curr;
        curr->next = curr;
    }

    if (FOUND == FALSE) return;

    if (prev == NULL)
    {
        FIRST = curr->next;

        if (curr == LAST)
            LAST = FIRST;
    } else {
        prev->next = curr->next;
        if (curr == LAST)
            LAST = prev;
    }

    char msg[50];
    sprintf(msg, tr("%s has left."), curr->data->callsign);
    PRINT_STATUS_MSG_NOIND(msg);

    pm_elem_free(&curr);
    COUNT--;
}

static void pm_mtl(char* callsign)
{
    if (gs_mssn_running() == TRUE)
    {
        char timeStr[80];
        gs_mssn_time_str((char*)&timeStr);
        gs_cmd_chat_callsign(callsign, (char*)&timeStr);
    } else {
        gs_cmd_chat_callsign(callsign, tr("Mission is not running now."));
    }
}

static void msg_delete(MSG_T* msg)
{
    switch (msg->type)
    {
        case PM_REQ_USR_JOIN:
        {
            break;
        }
        case PM_REQ_USR_LEFT:
        {
            free((uint2*)(msg->data));
            break;
        }
        case PM_REQ_MTL:
        {
            free((char*)(msg->data));
            break;
        }
        default:
            break;
    }

    free(msg);
}

static void msg_enqueue(MSG_T* msg)
{
    msg_queue_put(&MSG_Q, msg);
    pthread_cond_signal(&MSG_CND);
}

static void* pm_msg_dispatcher()
{
    msg_queue_clear(&MSG_Q, &msg_delete);

    MSG_T* msg;

    while(DO_WORK == TRUE)
    {
        msg = msg_queue_get(&MSG_Q);
        if (msg == NULL)
        {
            pthread_mutex_lock(&MSG_MTX);
            pthread_cond_wait(&MSG_CND, &MSG_MTX);
            pthread_mutex_unlock(&MSG_MTX);
        } else {
            switch (msg->type)
            {
                case PM_REQ_USR_JOIN:
                {
                    pm_user_join((D_PILOT_ELEM*)(msg->data));
                    break;
                }
                case PM_REQ_USR_LEFT:
                {
                    pm_user_left(*((uint2*)(msg->data)));
                    break;
                }
                case PM_REQ_MTL:
                {
                    pm_mtl((char*)(msg->data));
                    break;
                }

                default:
                    break;
            }
            msg_delete(msg);
        }
    }
    msg_queue_clear(&MSG_Q, &msg_delete);

    return NULL;
}

uint2 pm_pilot_count()
{
    return COUNT;
}
