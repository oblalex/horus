#include "circular_buffer.h"

#include <stdlib.h>
#include <string.h>

void cbuff_init(CBUFFER* _this, int capacity, int itemSize)
{
    _this->buffer = (void**) malloc(capacity*itemSize);
    _this->capacity = capacity;
    _this->start = 0;
    _this->end = 0;
    _this->active = 0;
}

void cbuff_push(CBUFFER* _this, void* item)
{
    _this->buffer[_this->end] = item;
    _this->end = (_this->end + 1) % _this->capacity;

    if (_this->active < _this->capacity)
    {
        _this->active++;
    } else {
        _this->start = (_this->start + 1) % _this->capacity;
    }
}

void* cbuff_retrieve(CBUFFER* _this)
{
    void* item;

    if (!(_this->active)) {return NULL;}

    item = _this->buffer[_this->start];
    _this->start = (_this->start + 1) % _this->capacity;

    _this->active--;
    return item;
}
