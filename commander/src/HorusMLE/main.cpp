#include "main_window.h"
#include <QApplication>
#include <QTime>
#include <QStyle>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    MainWindow w;
    w.show();
    
    return a.exec();
}
