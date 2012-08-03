/**
 * @file
 * 		Defined methods and structures for working with ini-files.
 */
#ifndef INI_H
#define INI_H

#include "common.h"
#include "l10n.h"

typedef struct ini_record
{
	char comments[255];
	char key[255];
	char value[255];
	struct ini_record* next;
} INI_RECORD;


typedef struct ini_section
{
	char comments[255];
	char name[255];
	
	INI_RECORD* record_first;
	INI_RECORD* record_last;
	unsigned int records_count;
	
	struct ini_section* next;
} INI_SECTION;

typedef struct
{	
	char* filepath;
	
	INI_SECTION* first;
	INI_SECTION* last;
	
	unsigned int sections_count;
	const char* error_msg;
} INI_CONTAINER;

INI_CONTAINER* ini_start(char* filepath);
void ini_end(INI_CONTAINER* this);
void ini_init(INI_CONTAINER** container, char* filepath);
void ini_load(INI_CONTAINER* this);
void ini_save(INI_CONTAINER* this);
void ini_save_as(INI_CONTAINER* this, char* filepath);
void ini_clear(INI_CONTAINER* this);

char* ini_value_get(INI_CONTAINER* this, const char* section_name, const char* key);
char* ini_value_with_comment_get(INI_CONTAINER* this, const char* section_name, const char* key, char* comment);
void ini_value_set(INI_CONTAINER* this, const char* sec, const char* key, const char* value);
void ini_value_with_comment_set(INI_CONTAINER* this, const char* section_name, const char* key, const char* value, const char* comment);

INI_SECTION* ini_section_get(INI_CONTAINER* this, const char* name);
void ini_section_add(INI_CONTAINER* this, const char* name, const char* comment);

INI_RECORD* ini_record_get(INI_SECTION* section, const char* key);
void ini_record_remove(INI_CONTAINER* this, const char* section_name, const char* key);
void ini_section_records_clear(INI_SECTION* section);

void ini_append(INI_CONTAINER* this, const char *name, const char *key, const char *value, const char *comment);

void trim_new_line(char *buffer);
BOOL ini_has_err(INI_CONTAINER* this);

#define INI_PARSING_ERR 	tr("Unable to parse ini-file")
#define INI_OPENING_ERR 	tr("Unable to open ini-file")

#define INI_MALLOC_ERR_RECORD 		tr("Can not allocate memory for ini-record")
#define INI_MALLOC_ERR_SECTION 		tr("Can not allocate memory for ini-section")
#define INI_MALLOC_ERR_CONTAINER 	tr("Can not allocate memory for ini-container")

#endif // INI_H
