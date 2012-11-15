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
    difficulty_view_page.cpp

HEADERS  += main_window.h \
    config_module.h \
    general_page.h \
    named_page.h \
    settings.h \
    ../gs_cfg_sections.h \
    difficulty_page.h \
    difficulty_flight_model_page.h \
    difficulty_subpage.h \
    difficulty_weapons_page.h \
    difficulty_view_page.h

FORMS    += main_window.ui \
    general_page.ui \
    difficulty_page.ui \
    difficulty_flight_model_page.ui \
    difficulty_weapons_page.ui \
    difficulty_view_page.ui

RESOURCES += \
    img.qrc

CONFIG += release
INCLUDEPATH += ../

win32 {
    RC_FILE = qapp.rc
    release: DESTDIR = ./
}

TRANSLATIONS = l10n/horusConfig_ru.ts
