#ifndef CIRCULAR_BUFFER_H
#define CIRCULAR_BUFFER_H

typedef struct CBUFFER
{
    void** buffer;
    int start;
    int end;
    int active;
    int capacity;
} CBUFFER;

void cbuff_init(CBUFFER* _this, int capacity, int itemSize);
void cbuff_push(CBUFFER* _this, void* item);
void* cbuff_retrieve(CBUFFER* _this);

#endif // CIRCULAR_BUFFER_H
