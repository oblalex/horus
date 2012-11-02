#-------------------------------------------------
#
# Project created by QtCreator 2012-11-01T22:26:57
#
#-------------------------------------------------

QT       += core gui xml

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = HorusMLE
TEMPLATE = app


SOURCES += main.cpp\
        main_window.cpp \
    map_list_view.cpp \
    list_file_helper.cpp \
    mission_elem.cpp \
    edge.cpp

HEADERS  += main_window.h \
    map_list_view.h \
    list_file_helper.h \
    ../domain/d_weather.h \
    ../domain/d_mission.h \
    mission_elem.h \
    edge.h

INCLUDEPATH += ../domain

FORMS    += main_window.ui

RESOURCES += \
    img.qrc

win32 {
  RESOURCES += \
    qapp.rc
}
