#include "main_window.h"
#include "ui_main_window.h"

#include <QDesktopWidget>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setWGeometry();
    setSplitterPos();
}

void MainWindow::setWGeometry()
{
    setGeometry(QStyle::alignedRect(
                    Qt::LeftToRight,
                    Qt::AlignCenter,
                    size(),
                    QApplication::desktop()->availableGeometry()));
}

void MainWindow::setSplitterPos()
{
    QList<int> sizes;
    int listW = qMin((width()*20)/100, 140);
    sizes << listW;
    sizes << width()-listW;
    ui->splitter->setSizes(sizes);
}

MainWindow::~MainWindow()
{
    delete ui;
}
