#include <stdlib.h>
#include "stack_str.h"

void push(STACK_STR* this, char* str)
{
	STACK_STR_NODE* new_node;
	new_node = (STACK_STR_NODE*)malloc(sizeof(STACK_STR_NODE));
	new_node->data = str;
	new_node->next = this->top;
		
	this->top = new_node;	
	this->size++;
}

char* pop(STACK_STR* this)
{	
	if(this->size==0)
	{
		return NULL;
	}
	
	STACK_STR_NODE *new_top;
	new_top = this->top->next;
	
	char* str = this->top->data;
	free(this->top);
	
	this->top = new_top;	
	this->size--;
	
	return str;
}