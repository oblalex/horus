#include "main_window.h"
#include "ui_main_window.h"
#include "general_page.h"
#include "settings.h"

#include <QDesktopWidget>

#define GRP_WND ("WND")
static QString KEY_POS_X = QString(GRP_WND).append("/x");
static QString KEY_POS_Y = QString(GRP_WND).append("/y");
static QString KEY_POS_W = QString(GRP_WND).append("/w");
static QString KEY_POS_H = QString(GRP_WND).append("/h");

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setWGeometry();
    setSplitterPos();
    addPages();
    load();

    connect(ui->buttons, SIGNAL(clicked(QAbstractButton*)), this, SLOT(onButtonClicked(QAbstractButton*)));
}

void MainWindow::setWGeometry()
{
    QRect defRect = QStyle::alignedRect(
                Qt::LeftToRight,
                Qt::AlignCenter,
                size(),
                QApplication::desktop()->availableGeometry());
    QRect curRect;

    curRect.setX(Settings::horusValue(KEY_POS_X, defRect.x()).toInt());
    curRect.setY(Settings::horusValue(KEY_POS_Y, defRect.y()).toInt());
    curRect.setWidth(Settings::horusValue(KEY_POS_W, defRect.width()).toInt());
    curRect.setHeight(Settings::horusValue(KEY_POS_H, defRect.height()).toInt());

    setGeometry(curRect);
}

void MainWindow::setSplitterPos()
{
    QList<int> sizes;
    int listW = qMin((width()*20)/100, 140);
    sizes << listW;
    sizes << width()-listW;
    ui->splitter->setSizes(sizes);
}

void MainWindow::addPages()
{
    GeneralPage* general = new GeneralPage;
    new QListWidgetItem(
                QIcon(":/img/general.png"),
                general->pageName(),
                ui->list,
                QListWidgetItem::UserType);
    ui->stack->addWidget(general);
    addChild(general);

    ui->stack->setCurrentIndex(0);
    ui->list->setCurrentRow(0);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::save()
{
    QRect curRect = geometry();
    Settings::setHorusValue(KEY_POS_X, curRect.x());
    Settings::setHorusValue(KEY_POS_Y, curRect.y());
    Settings::setHorusValue(KEY_POS_W, curRect.width());
    Settings::setHorusValue(KEY_POS_H, curRect.height());

    saveChildren();
    Settings::save();
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

void MainWindow::on_list_currentItemChanged(QListWidgetItem *current, QListWidgetItem *previous)
{
    if (!current)
        current = previous;

    ui->stack->setCurrentIndex(ui->list->row(current));
}

void MainWindow::closeEvent(QCloseEvent *event)
{
    Q_UNUSED(event)
    saveWGeometry();
}

void MainWindow::saveWGeometry()
{
    QRect curRect = geometry();
    Settings::setHorusValue(KEY_POS_X, curRect.x());
    Settings::setHorusValue(KEY_POS_Y, curRect.y());
    Settings::setHorusValue(KEY_POS_W, curRect.width());
    Settings::setHorusValue(KEY_POS_H, curRect.height());
    Settings::save();
}
