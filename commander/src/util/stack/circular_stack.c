#include "circular_stack.h"

#include <stdlib.h>
#include <string.h>

void cstack_init(CSTACK* this, int capacity, int itemSize)
{
    this->buffer = (void**) malloc(capacity*itemSize);
    this->capacity = capacity;
    this->start = 0;
    this->active = 0;
}

void cstack_push(CSTACK* this, void* item)
{
    this->buffer[this->start] = item;
    this->start = (this->start + 1) % this->capacity;

    if (this->active < this->capacity)
        this->active++;
}

void* cstack_retrieve(CSTACK* this)
{
    void* item;

    if (!(this->active)) {return NULL;}

    this->start = (this->start - 1);
    if (this->start <0)
        this->start = this->capacity + this->start;

    item = this->buffer[this->start];

    this->active--;
    return item;
}
