#-------------------------------------------------
#
# Project created by QtCreator 2012-11-15T10:05:37
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = horusConfig
TEMPLATE = app


SOURCES += main.cpp\
        main_window.cpp \
    config_module.cpp \
    general_page.cpp \
    named_page.cpp \
    settings.cpp \
    difficulty_page.cpp \
    difficulty_flight_model_page.cpp \
    difficulty_subpage.cpp \
    difficulty_weapons_page.cpp \
    difficulty_view_page.cpp \
    difficulty_map_icons_page.cpp \
    difficulty_var_page.cpp \
    net_page.cpp \
    ../util/str.c

HEADERS  += main_window.h \
    config_module.h \
    general_page.h \
    named_page.h \
    settings.h \
    difficulty_page.h \
    difficulty_flight_model_page.h \
    difficulty_subpage.h \
    difficulty_weapons_page.h \
    difficulty_view_page.h \
    difficulty_map_icons_page.h \
    difficulty_var_page.h \
    net_page.h \
    ../gs_cfg_grp.h \
    ../sys_cfg_grp.h \
    ../sys_cfg_key.h \
    ../gs_cfg_key.h \
    ../util/str.h \
    config.h

FORMS    += main_window.ui \
    general_page.ui \
    difficulty_page.ui \
    difficulty_flight_model_page.ui \
    difficulty_weapons_page.ui \
    difficulty_view_page.ui \
    difficulty_map_icons_page.ui \
    difficulty_var_page.ui \
    net_page.ui

RESOURCES += \
    img.qrc

CONFIG += release
INCLUDEPATH += ../ \
    ../util/

win32 {
    RC_FILE = qapp.rc
    release: DESTDIR = ./

}

TRANSLATIONS = l10n/horusConfig_ru.ts
