#include "msg_queue.h"
#include <stdlib.h>

void msg_queue_put(MSG_QUEUE* this, MSG_T* msg)
{
    MSG_QUEUE_ELEM* elem = (MSG_QUEUE_ELEM*)malloc(sizeof(MSG_QUEUE_ELEM));
    elem->msg = msg;
    elem->next = NULL;

    if (this->head == NULL)
    {
        this->head = elem;
        this->tail = elem;
    } else {
        this->tail->next = elem;
        this->tail = elem;
    }
}

MSG_T* msg_queue_get(MSG_QUEUE* this)
{
    MSG_T* result = NULL;
    if (this->head != NULL)
    {
        MSG_QUEUE_ELEM* elem = this->head;
        result = elem->msg;
        free(elem);
        this->head = this->head->next;
    }
    return result;
}

void msg_queue_clear(MSG_QUEUE* this, void (*delete_fn)(MSG_T*))
{
    MSG_QUEUE_ELEM* curr = this->head;
    MSG_QUEUE_ELEM* prev;

    while (curr != NULL)
    {
        prev = curr;
        curr = curr->next;

        (*delete_fn)(prev->msg);
        free(prev);
    }

    this->head = NULL;
    this->tail = NULL;
}
