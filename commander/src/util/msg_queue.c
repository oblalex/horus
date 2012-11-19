#include "msg_queue.h"
#include <stdlib.h>

void msg_queue_put(MSG_QUEUE* _this, MSG_T* msg)
{
    MSG_QUEUE_ELEM* elem = (MSG_QUEUE_ELEM*)malloc(sizeof(MSG_QUEUE_ELEM));
    elem->msg = msg;
    elem->next = NULL;

    if (_this->head == NULL)
    {
        _this->head = elem;
        _this->tail = elem;
    } else {
        _this->tail->next = elem;
        _this->tail = elem;
    }
}

MSG_T* msg_queue_get(MSG_QUEUE* _this)
{
    MSG_T* result = NULL;
    if (_this->head != NULL)
    {
        MSG_QUEUE_ELEM* elem = _this->head;
        result = elem->msg;
        free(elem);
        _this->head = _this->head->next;
    }
    return result;
}

void msg_queue_clear(MSG_QUEUE* _this, void (*delete_fn)(MSG_T*))
{
    MSG_QUEUE_ELEM* curr = _this->head;
    MSG_QUEUE_ELEM* prev;

    while (curr != NULL)
    {
        prev = curr;
        curr = curr->next;

        (*delete_fn)(prev->msg);
        free(prev);
    }

    _this->head = NULL;
    _this->tail = NULL;
}
