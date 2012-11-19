#include "ini.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "str.h"

INI_CONTAINER* ini_start(char* filepath)
{
	INI_CONTAINER* container = NULL;

	ini_init(&container, filepath);

	if (container != NULL)
		ini_load(container);
	
	return container;
}

void ini_init(INI_CONTAINER** container, char* filepath)
{
	*container = (INI_CONTAINER*)malloc(sizeof(INI_CONTAINER));

	if (*container == NULL)
		return;

	(*container)->sections_count = 0;	
	(*container)->first = NULL;
	(*container)->last = NULL;
	
	(*container)->filepath = str_copy(filepath);
}

void ini_load(INI_CONTAINER* _this)
{
	FILE *in_stream;
	char buffer[255];
	char comments[1024];
	char current_section[255];	
	char key[255];
	char value[255];
	char *pdest;
	int  index;

	strcpy(comments, "");
	strcpy(current_section, "");
	_this->error_msg = NULL;

	if ((in_stream = fopen(_this->filepath, "r" )) != NULL)
	{
		while (fgets(buffer, sizeof(buffer), in_stream) != NULL)
		{
			trim_new_line(buffer);
			switch (buffer[0])
			{
				case '[' :
					pdest = strrchr(buffer, ']');
					if (pdest == NULL)
					{
						fclose(in_stream);
						_this->error_msg = INI_PARSING_ERR;
						return;
					}
					index = pdest - buffer;
					memcpy(current_section, buffer + 1, index - 1);
					current_section[index - 1] = '\0';
					ini_section_add(_this, current_section, comments);	
					if (ini_has_err(_this) == TRUE) {
						fclose(in_stream);
						return;
					}
					strcpy(comments, "");
					break;
				case '#' :
				case ';' :
					if(strlen(comments) > 0)
						strcat(comments, "\n");
					strcat(comments, buffer);
					break;
				default :
                    if (strlen(buffer) == 0) continue;
					pdest = strrchr(buffer, '=');
					if (pdest == NULL) 
					{
						fclose(in_stream);
                        _this->error_msg = INI_PARSING_ERR;
						return;
					}
					index = pdest - buffer;
					memcpy(key, buffer, index);
					key[index] = '\0';
					memcpy(value, buffer + index + 1, strlen(buffer)-index);
					
					if (strcmp(current_section, "") == 0)
					{
						fclose(in_stream);
                        _this->error_msg = INI_PARSING_ERR;
						return;
					}
					else
					{
						ini_append(_this, current_section, key, value, comments);
						if (ini_has_err(_this) == TRUE) {
							fclose(in_stream);
							return;
						}
						strcpy(comments, "");
					}
					break;
			}
		}
		fclose(in_stream);
	}
	else
	{
		_this->error_msg = INI_OPENING_ERR;
	}
}

void trim_new_line(char *buffer)
{
	if (buffer[strlen(buffer)-1] == '\n')
		buffer[strlen(buffer)-1] = '\0';
}

void ini_append(INI_CONTAINER* _this, const char *name, const char *key, const char *value, const char *comment)
{
	INI_SECTION* section;
	INI_RECORD* record;
	
	_this->error_msg = NULL;
	
	section = ini_section_get(_this, name);

	if (section != NULL)
	{
		record = ini_record_get(section, key);
		if (record == NULL)
		{
			record = (INI_RECORD*)malloc(sizeof(INI_RECORD));
			if (record == NULL)
			{
				_this->error_msg = INI_MALLOC_ERR_RECORD;
				return;
			}
			record->next = NULL;	
			
			if((comment[0] != '#' || comment[0] != ';') && (strlen(comment) > 0))
				sprintf(record->comments, "#%s", comment);
			else
				strcpy(record->comments, comment);
			
			strcpy(record->key, key);
			strcpy(record->value, value);			
			section->records_count++;

			if (section->record_first == NULL)
			{
				section->record_first = record;
				section->record_last  = record;
			}
			else
			{
				section->record_last->next = record;
				section->record_last = record;
			}			
		}
		else
		{
			if ((comment[0] != '#' || comment[0] != ';') && (strlen(comment) > 0))
				sprintf(record->comments, "#%s", comment);
			else
				strcpy(record->comments, comment);
			
			strcpy(record->key, key);
			strcpy(record->value, value);
		}
		
	}
	else
	{
		ini_section_add(_this, name, "");
		if (ini_has_err(_this) == FALSE)
			ini_append(_this, name, key, value, comment);
	}
}

void ini_section_add(INI_CONTAINER* _this, const char* name, const char* comment)
{
	INI_SECTION* section;
	section = ini_section_get(_this, name);
	
	_this->error_msg = NULL;

	if (section == NULL)
	{
		section = (INI_SECTION*)malloc(sizeof(INI_SECTION));
			
		if (section == NULL)
		{
			_this->error_msg = INI_MALLOC_ERR_SECTION;
			return;
		}
		
		strcpy(section->name, name);

		if ((comment[0] != '#' || comment[0] != ';') && (strlen(comment) > 0))
			sprintf(section->comments,"#%s",comment);
		else
			strcpy(section->comments, comment);

		section->record_first = NULL;
		section->record_last = NULL;
		section->next = NULL;
		section->records_count = 0;

		_this->sections_count++;

		if (_this->first == NULL)
		{
			_this->first = section;
			_this->last  = section;
		}
		else
		{
			_this->last->next = section;
			_this->last = section;
		}	
	}
	else
	{
		strcpy(section->name, name);
		if ((comment[0] != '#' || comment[0] != ';') && (strlen(comment) > 0))
			sprintf(section->comments, "#%s", comment);
		else
			strcpy(section->comments, comment);
	}
}

INI_SECTION* ini_section_get(INI_CONTAINER* _this, const char* name)
{
	BOOL found = FALSE;
	INI_SECTION* section = _this->first;
	while (section != NULL)
	{	
		if (strcmp(section->name,name) == 0)
		{
			found = TRUE;
			break;
		}		
		section = section->next;
	}

	if (found == TRUE)
		return section;
	else
		return NULL;
}

INI_RECORD* ini_record_get(INI_SECTION* section, const char* key)
{
	BOOL found = FALSE;
	INI_RECORD* record;
	
	record = section->record_first;

	while (record != NULL)
	{
		if(strcmp(key, record->key) == 0)
		{
			found = TRUE;
			break;
		}
		record = record->next;
	}

	if (found == TRUE)
	{
		return record;
	}
	else
	{
		return NULL;
	}
}

void ini_end(INI_CONTAINER* _this)
{
	ini_save(_this);
	ini_clear(_this);
}

void ini_save_as(INI_CONTAINER* _this, char* filepath)
{
	FILE* stream;
	INI_SECTION* section = _this->first;
	INI_RECORD* record;
	
	_this->error_msg = NULL;
	
	if ((stream = fopen(filepath, "w")) == NULL)
	{
		_this->error_msg = INI_OPENING_ERR;
		return;
	}

	while (section != NULL)
	{
		if (strlen(section->comments) != 0)
		{
			fprintf(stream,"%s\n", section->comments);		
		}
		fprintf(stream,"[%s]\n", section->name);

		record = section->record_first;
		while (record != NULL)
		{
			if (strlen(record->comments) != 0)
			{
				fprintf(stream,"%s\n", record->comments);				
			}
			fprintf(stream,"%s=%s\n", record->key, record->value);
			
			record = record->next;
		}
		section = section->next;		
	}	
	fclose(stream);
}

void ini_save(INI_CONTAINER* _this)
{
	ini_save_as(_this, _this->filepath);
}

void ini_clear(INI_CONTAINER* _this)
{
	if(_this == NULL)
		return;
	
	INI_SECTION* section;
	section = _this->first;
	while (section != NULL)
	{
		_this->first = section->next;
		_this->sections_count--;
		ini_section_records_clear(section);
		free(section);
		section = _this->first;
	}
	free(_this->filepath);
}

void ini_section_records_clear(INI_SECTION* section)
{
	INI_RECORD* record;

	if(section == NULL)
		return;

	record = section->record_first;
	while (record != NULL)
	{
		section->record_first = record->next;
		section->records_count--;
		free(record);
		record = section->record_first;
	}
}

void ini_record_remove(INI_CONTAINER* _this, const char* section_name, const char* key)
{	
	INI_SECTION* section = ini_section_get(_this, section_name);
	
	if (section == NULL)
		return;

	INI_RECORD *r1,*r2;
	
	r1 = section->record_first;
	
	if (r1 == NULL)
		return;
	
	if (strcmp(key, r1->key) == 0)
	{
		section->record_first = r1->next;
		section->records_count--;
		free(r1);
	}
	
	while (r1 != NULL)
	{
		if (r1->next != NULL)
		{
			if (strcmp(key, r1->next->key) == 0)
			{	
				r2 = r1->next;				
				r1->next = r1->next->next;
				section->records_count--;
				free(r2);
				break;
			}
		}		
		r1 = r1->next;
	}
}

char* ini_value_get(INI_CONTAINER* _this, const char* section_name, const char* key)
{
	INI_RECORD* result = ini_record_get(ini_section_get(_this, section_name), key);
	if (result != NULL)
		return result->value;
	else
		return NULL;
}

char* ini_value_with_comment_get(INI_CONTAINER* _this, const char* section_name, const char* key, char* comment)
{
	INI_RECORD* result = ini_record_get(ini_section_get(_this, section_name), key);
	if (result != NULL)
	{
		strcpy(comment, result->comments);
		return result->value;
	} else {
		strcpy(comment, "");
		return NULL;	  
	}
}

void ini_value_set(INI_CONTAINER* _this, const char* section_name, const char* key, const char* value)
{
	ini_value_with_comment_set(_this, section_name, key, value, "");
}

void ini_value_with_comment_set(INI_CONTAINER* _this, const char* section_name, const char* key, const char* value, const char* comment)
{
	ini_append(_this, section_name, key, value, comment);
}

BOOL ini_has_err(INI_CONTAINER* _this)
{
	if (_this->error_msg == NULL)
		return FALSE;
	else
		return TRUE;
}
