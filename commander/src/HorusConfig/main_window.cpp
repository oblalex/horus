#include "main_window.h"
#include "ui_main_window.h"

#include <QDesktopWidget>

#include <iostream>
using namespace std;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setWGeometry();
    setSplitterPos();

    load();

    connect(ui->buttons, SIGNAL(clicked(QAbstractButton*)), this, SLOT(onButtonClicked(QAbstractButton*)));
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

void MainWindow::save()
{
    saveChildren();
}

void MainWindow::load()
{
    loadChildren();
}

void MainWindow::loadDefaults()
{
    loadChildrenDefaults();
}

void MainWindow::onButtonClicked(QAbstractButton *button)
{
    switch (ui->buttons->buttonRole(button))
    {
        case QDialogButtonBox::ResetRole:
            loadDefaults();
            break;
        case QDialogButtonBox::RejectRole:
            close();
            break;
        case QDialogButtonBox::AcceptRole:
            save();
            break;
        default:;
    }
}
