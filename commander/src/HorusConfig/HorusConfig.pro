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
    config_module.cpp

HEADERS  += main_window.h \
    config_module.h

FORMS    += main_window.ui

RESOURCES += \
    img.qrc

CONFIG += release

win32 {
    RC_FILE = qapp.rc
    release: DESTDIR = ./
}
