#include "main_window.h"
#include "settings.h"

#include <QApplication>
#include <QTranslator>
#include <QLibraryInfo>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Settings::init();

    QString lang = Settings::getLangCode();
    QTranslator qtTranslator;
    qtTranslator.load("qt_" + lang, QLibraryInfo::location(QLibraryInfo::TranslationsPath));
    a.installTranslator(&qtTranslator);

    QTranslator myappTranslator;
    myappTranslator.load(QString("l10n/horusConfig_").append(lang));
    a.installTranslator(&myappTranslator);

    MainWindow w;
    w.show();
    
    return a.exec();
}
