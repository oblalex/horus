/** 
 * @file
 * 		Defines stack for storing strings
 */
#ifndef STACK_STR_H
#define STACK_STR_H

/**
 * Element of string stack.
 */
typedef struct stack_str_node
{
	/** Pointer to element's data. */
	char* data;
	/** Pointer to next element. */
	struct stack_str_node* next;
} STACK_STR_NODE;

/**
 * String stack descriptor.
 */
typedef struct
{
	/** Current number of elements in stack. */
	int size;
	/** Pointer to the top element. */
	STACK_STR_NODE* top;
} STACK_STR;

/**
 * Push string to stack.
 *
 * @param this
 * 		Pointer to stack.
 * @param str
 * 		Pointer to string to add.
 */
void push(STACK_STR* this, char* str);

/**
 * Pop string from stack.
 *
 * @param this
 * 		Pointer to stack.
 * @return
 * 		Pointer to string or NULL if stack is empty.
 */
char* pop(STACK_STR* this);

#endif // STACK_STR_H