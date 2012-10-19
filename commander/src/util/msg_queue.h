#ifndef MSG_QUEUE_H
#define MSG_QUEUE_H

typedef struct MSG_T
{
    int type;
    void* data;
} MSG_T;

typedef struct MSG_QUEUE_ELEM
{
    MSG_T* msg;
    struct MSG_QUEUE_ELEM* next;
} MSG_QUEUE_ELEM;

typedef struct
{
    MSG_QUEUE_ELEM* head;
    MSG_QUEUE_ELEM* tail;
} MSG_QUEUE;

void msg_queue_put(MSG_QUEUE* this, MSG_T* msg);
MSG_T* msg_queue_get(MSG_QUEUE* this);
void msg_queue_clear(MSG_QUEUE* this, void (*delete_fn)(MSG_T*));

#endif // MSG_QUEUE_H
