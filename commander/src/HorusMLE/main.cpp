#include "main_window.h"
#include <QApplication>

#include <QDesktopWidget>
#include <QTranslator>
#include <QLibraryInfo>
#include "settings.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    Settings settings(false);
    QTranslator qtTranslator;
    qtTranslator.load("qt_" + settings.lang, QLibraryInfo::location(QLibraryInfo::TranslationsPath));
    a.installTranslator(&qtTranslator);

    QTranslator myappTranslator;
    myappTranslator.load(QString("l10n/horusMLE_").append(settings.lang));
    a.installTranslator(&myappTranslator);

    MainWindow w;
    w.show();
    
    return a.exec();
}
