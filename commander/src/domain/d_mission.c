#include "d_mission.h"
#include <stdlib.h>
#include <string.h>

void d_mission_init(D_MISSION** _this)
{
    d_mission_init_lazy(_this);
    d_weather_init(&((*_this)->weather));
}

void d_mission_init_lazy(D_MISSION** _this)
{
    if ((*_this) == NULL)
        (*_this) = (struct D_MISSION*) malloc(sizeof (struct D_MISSION));

    (*_this)->name      = NULL;
    (*_this)->path      = NULL;
    (*_this)->sDuration = 0;
    (*_this)->weather   = NULL;
}

void d_mission_free(D_MISSION** _this)
{
    if ((*_this) == NULL) return;

    free((*_this)->name);
    free((*_this)->path);
    d_weather_free(&((*_this)->weather));
    free(*_this);

    *_this = NULL;
}

void d_mission_name_set(D_MISSION* _this, char* name)
{
    if (_this->name != NULL) free(_this->name);
    _this->name = (char*)malloc(sizeof(char)*strlen(name)+1);
    strcpy(_this->name, name);
}

void d_mission_path_set(D_MISSION* _this, char* path)
{
    if (_this->path != NULL) free(_this->path);
    _this->path = (char*)malloc(sizeof(char)*strlen(path)+1);
    strcpy(_this->path, path);
}
