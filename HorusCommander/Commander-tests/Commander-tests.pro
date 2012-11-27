#-------------------------------------------------
#
# Project created by QtCreator 2012-11-27T20:27:54
#
#-------------------------------------------------

QT += network xml gui testlib

TARGET = commander_tests
CONFIG += console
CONFIG -= app_bundle

TEMPLATE = app

SOURCES += \
    footest.cpp \
    main.cpp
DEFINES += SRCDIR=\\\"$$PWD/\\\"

HEADERS += \
    footest.h
