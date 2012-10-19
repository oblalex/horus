#include "circular_buffer.h"

#include <stdlib.h>
#include <string.h>

void cbuff_init(CBUFFER* this, int capacity, int itemSize)
{
    this->buffer = (void**) malloc(capacity*itemSize);
    this->capacity = capacity;
    this->start = 0;
    this->end = 0;
    this->active = 0;
}

void cbuff_push(CBUFFER* this, void* item)
{
    this->buffer[this->end] = item;
    this->end = (this->end + 1) % this->capacity;

    if (this->active < this->capacity)
    {
        this->active++;
    } else {
        this->start = (this->start + 1) % this->capacity;
    }
}

void* cbuff_retrieve(CBUFFER* this)
{
    void* item;

    if (!(this->active)) {return NULL;}

    item = this->buffer[this->start];
    this->start = (this->start + 1) % this->capacity;

    this->active--;
    return item;
}
