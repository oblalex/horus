#ifndef CIRCULAR_STACK_H
#define CIRCULAR_STACK_H

typedef struct CSTACK
{
    void** buffer;
    int start;
    int active;
    int capacity;
} CSTACK;

void cstack_init(CSTACK* this, int capacity, int itemSize);
void cstack_push(CSTACK* this, void* item);
void* cstack_retrieve(CSTACK* this);

#endif // CIRCULAR_STACK_H
