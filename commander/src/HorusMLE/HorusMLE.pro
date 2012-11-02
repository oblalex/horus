#-------------------------------------------------
#
# Project created by QtCreator 2012-11-01T22:26:57
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = HorusMLE
TEMPLATE = app


SOURCES += main.cpp\
        main_window.cpp \
    map_list_view.cpp

HEADERS  += main_window.h \
    map_list_view.h

FORMS    += main_window.ui

OTHER_FILES +=

RESOURCES += \
    img.qrc

win32 {
  RESOURCES += \
    qapp.rc
}
