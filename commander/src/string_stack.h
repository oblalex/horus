#ifndef STRING_STACK_H
#define STRING_STACK_H

#include <stdlib.h>

typedef struct node
{
	char* data;
	struct node* next;
} stack_node;

typedef struct stack
{
	int count;
	stack_node* top;
} STRING_STACK;

void push(STRING_STACK*, char*);
char* pop(STRING_STACK*);

void push(STRING_STACK* this, char* str)
{
	stack_node* new_node;
	new_node = (stack_node*)malloc(sizeof(stack_node));
	new_node->data = str;
	new_node->next = this->top;
		
	this->top = new_node;	
	this->count = this->count+1;
}

char* pop(STRING_STACK* this)
{	
	if(this->count==0)
	{
		return NULL;
	}
	
	stack_node *new_top;
	new_top = this->top->next;
	
	char* str = this->top->data;
	free(this->top);
	this->top = new_top;
	
	this->count = this->count-1;
	
	return str;
}

#endif // STRING_STACK_H