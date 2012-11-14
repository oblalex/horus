#-------------------------------------------------
#
# Project created by QtCreator 2012-11-01T22:26:57
#
#-------------------------------------------------

QT       += core gui xml

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = horusMLE
TEMPLATE = app


SOURCES += main.cpp\
        main_window.cpp \
    list_file_helper.cpp \
    mission_elem.cpp \
    edge.cpp \
    mission_dialog.cpp \
    mission_list_view.cpp \
    about_dialog.cpp \
    settings.cpp

HEADERS  += main_window.h \
    list_file_helper.h \
    ../domain/d_weather.h \
    ../domain/d_mission.h \
    mission_elem.h \
    edge.h \
    mission_dialog.h \
    mission_list_view.h \
    about_dialog.h \
    settings.h

INCLUDEPATH += ../domain

FORMS    += main_window.ui \
    mission_dialog.ui \
    about_dialog.ui

RESOURCES += \
    img.qrc

CONFIG += release

win32 {
    RC_FILE = qapp.rc
    release: DESTDIR = ./
}

TRANSLATIONS = l10n/horusMLE_ru.ts
