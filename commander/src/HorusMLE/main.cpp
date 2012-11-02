#include "main_window.h"
#include <QApplication>
#include <QTime>
#include <QStyle>
#include <QDesktopWidget>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    qsrand(QTime(0,0,0).secsTo(QTime::currentTime()));

    MainWindow w;
    w.setGeometry(
            QStyle::alignedRect(
            Qt::LeftToRight,
            Qt::AlignCenter,
            w.size(),
            QApplication::desktop()->availableGeometry()));
    w.show();
    
    return a.exec();
}
