#include "main_window.h"
#include "ui_main_window.h"
#include "mission_dialog.h"
#include "about_dialog.h"
#include <QSplitter>
#include <QMessageBox>

#include <iostream>
using namespace std;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    setWindowTitle(tr("Horus MLE"));

    createMenu();
    createMainBar();
    createNavBar();
    createToolBar();
    createStatusBar();
    createCentralWidget();

    lfHelper = new ListFileHelper(MLV);
    loadAction->trigger();

    onMissionDeselected();

    connect(qApp, SIGNAL(aboutToQuit()), this, SLOT(onAboutToQuit()));
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::createMenu()
{
    QMenu* mainMenu = new QMenu(tr("&Main"), this);
    ui->menuBar->addMenu(mainMenu);

    loadAction = new QAction(tr("&Load"), this);
    connect(loadAction, SIGNAL(triggered()), this, SLOT(onLoadAction()));
    loadAction->setIcon(QIcon((":/img/load.png")));
    loadAction->setShortcut(QKeySequence(Qt::CTRL + Qt::Key_L));
    mainMenu->addAction(loadAction);

    clearAction = new QAction(tr("&Clear"), this);
    connect(clearAction, SIGNAL(triggered()), this, SLOT(onClearAction()));
    clearAction->setIcon(QIcon((":/img/clear.png")));
    clearAction->setShortcut(QKeySequence(Qt::CTRL + Qt::SHIFT + Qt::Key_C));
    mainMenu->addAction(clearAction);

    saveAction = new QAction(tr("&Save"), this);
    connect(saveAction, SIGNAL(triggered()), this, SLOT(onSaveAction()));
    saveAction->setIcon(QIcon((":/img/save.png")));
    saveAction->setShortcut(QKeySequence(Qt::CTRL + Qt::Key_S));
    mainMenu->addAction(saveAction);

    mainMenu->addSeparator();

    quitAction = new QAction(tr("&Quit"), this);
    connect(quitAction, SIGNAL(triggered()), this, SLOT(onQuitAction()));
    quitAction->setIcon(QIcon((":/img/quit.png")));
    quitAction->setShortcut(QKeySequence(Qt::CTRL + Qt::Key_X));
    mainMenu->addAction(quitAction);

    QMenu* helpMenu = new QMenu(tr("&Help"), this);
    ui->menuBar->addMenu(helpMenu);

    aboutAction = new QAction(tr("&About"), this);
    connect(aboutAction, SIGNAL(triggered()), this, SLOT(onAboutAction()));
    aboutAction->setIcon(QIcon((":/img/info.png")));
    aboutAction->setShortcut(QKeySequence(Qt::CTRL + Qt::SHIFT + Qt::Key_A));
    helpMenu->addAction(aboutAction);
}

void MainWindow::createMainBar()
{
    ui->mainBar->addAction(loadAction);
    ui->mainBar->addAction(clearAction);
    ui->mainBar->addAction(saveAction);
}

void MainWindow::createNavBar()
{
    QList<QKeySequence> zoomInKeys;
    zoomInKeys << QKeySequence(Qt::CTRL + Qt::Key_Plus);
    zoomInKeys << QKeySequence(Qt::CTRL + Qt::Key_Equal);

    zoomInAction = new QAction(tr("Zoom In"), this);
    zoomInAction->setIcon(QIcon((":/img/zoom_in.png")));
    zoomInAction->setShortcuts(zoomInKeys);
    ui->navBar->addAction(zoomInAction);

    zoomOutAction = new QAction(tr("Zoom Out"), this);
    zoomOutAction->setIcon(QIcon((":/img/zoom_out.png")));
    zoomOutAction->setShortcut(QKeySequence(Qt::CTRL + Qt::Key_Minus));
    ui->navBar->addAction(zoomOutAction);
}

void MainWindow::createToolBar()
{
    ui->toolBar->setIconSize(QSize(18, 18));

    createToolActions();
}

void MainWindow::createToolActions()
{
    newAction = new QAction(tr("&New"), this);
    connect(newAction, SIGNAL(triggered()), this, SLOT(onNewAction()));
    newAction->setIcon(QIcon((":/img/new.png")));
    newAction->setShortcut(QKeySequence(Qt::CTRL + Qt::Key_N));
    ui->toolBar->addAction(newAction);

    editAction = new QAction(tr("&Edit"), this);
    connect(editAction, SIGNAL(triggered()), this, SLOT(onEditAction()));
    editAction->setIcon(QIcon((":/img/edit.png")));
    editAction->setShortcut(QKeySequence(Qt::CTRL + Qt::Key_E));
    ui->toolBar->addAction(editAction);

    delAction = new QAction(tr("&Delete"), this);
    connect(delAction, SIGNAL(triggered()), this, SLOT(onDelAction()));
    delAction->setIcon(QIcon((":/img/delete.png")));
    delAction->setShortcut(QKeySequence(Qt::Key_Delete));
    ui->toolBar->addAction(delAction);
}

void MainWindow::createStatusBar()
{
    missionsLb = new QLabel(this);
    missionsLb->setAlignment(Qt::AlignLeft | Qt::AlignVCenter);
    missionsLb->setMargin(2);
    ui->statusBar->addWidget(missionsLb);

    missionLb = new QLabel(this);
    missionLb->setAlignment(Qt::AlignLeft | Qt::AlignVCenter);
    missionLb->setMargin(2);
    ui->statusBar->addWidget(missionLb);
}

void MainWindow::createCentralWidget()
{
    MLV = new MissionListView(this);

    connect(zoomInAction, SIGNAL(triggered()), MLV, SLOT(zoomIn()));
    connect(zoomOutAction, SIGNAL(triggered()), MLV, SLOT(zoomOut()));
    connect(MLV, SIGNAL(missionSelected()), this, SLOT(onMissionSelected()));
    connect(MLV, SIGNAL(missionDeselected()), this, SLOT(onMissionDeselected()));

    setCentralWidget(MLV);
    MLV->setFocus();
}

bool MainWindow::isListEmpty()
{
    return MLV->scene->items().count()==0;
}

void MainWindow::onListLoaded()
{
    saveAction->setEnabled(true);

    missionsLb->clear();

    if (isListEmpty())
        onListEmpty();
    else
        onListNonEmpty();
}

void MainWindow::onListNonLoaded()
{
    missionsLb->setText(tr("Mission list is not loaded."));
    saveAction->setEnabled(false);

    onListEmpty();
}

void MainWindow::onListEmpty()
{
    if (missionsLb->text().isEmpty())
        missionsLb->setText(tr("Mission list is empty."));

    clearAction->setEnabled(false);

    zoomInAction->setEnabled(false);
    zoomOutAction->setEnabled(false);

    editAction->setEnabled(false);
    delAction->setEnabled(false);
}

void MainWindow::onListNonEmpty()
{
    redrawMissionsCount();

    clearAction->setEnabled(true);
    saveAction->setEnabled(true);

    zoomInAction->setEnabled(true);
    zoomOutAction->setEnabled(true);
}

void MainWindow::redrawMissionsCount()
{
    missionsLb->setText(tr("Missions: ")+QString::number(MLV->missionsCount())+".");
}

void MainWindow::onQuitAction()
{
    qApp->quit();
}

void MainWindow::onAboutAction()
{
    AboutDialog dlg;
    dlg.exec();
}

void MainWindow::onLoadAction()
{
    lfHelper->loadToView();
    if (lfHelper->isLoaded())
        onListLoaded();
    else
        onListNonLoaded();
}

void MainWindow::onClearAction()
{
    if (MLV->missionsCount()==0) return;

    MLV->missionsClear();
    MLV->scene->update();
    MLV->setChanged(true);
    redrawMissionsCount();
    onListEmpty();
}

void MainWindow::onSaveAction()
{
    lfHelper->saveFromView();
    MLV->setChanged(false);
}

void MainWindow::onNewAction()
{
    MissionElem* active = MLV->getActive();
    MissionElem* me = new MissionElem(MLV);
    MLV->setActive(me);

    MissionDialog dlg(MLV);
    if (dlg.exec()==QDialog::Accepted)
    {
        me->setPos(0, 0);
        MLV->addMission(me);
        MLV->setChanged(true);
        redrawMissionsCount();
        onListNonEmpty();
        onMissionSelected();
    } else {
        MLV->setActive(active);
        delete me;
    }
}

void MainWindow::onEditAction()
{
    MissionElem* me = MLV->getActive();
    if (me==NULL) return;

    MissionDialog dlg(MLV, true);
    if (dlg.exec()==QDialog::Accepted)
    {
        me->update();
        MLV->setChanged(true);
    }
}

void MainWindow::onDelAction()
{
    MissionElem* me = MLV->getActive();
    if (me==NULL) return;

    switch(QMessageBox::question(
               this,
               tr("Deleting mission"),
               tr("Are you sure about deleting") + " \"" + QString(me->data.name) + "\" ?",
               tr("Yes"), tr("No"),
               0, 1))
    {
        case 0:
            MLV->rmMission(me);
            redrawMissionsCount();
            onMissionDeselected();
            MLV->setChanged(true);
            if (MLV->missionsCount()==0)
                onListEmpty();
            break;
        default:
            break;
    }
}

void MainWindow::onMissionSelected()
{
    editAction->setEnabled(true);
    delAction->setEnabled(true);
    missionLb->setText("\""+QString(MLV->getActive()->data.name) +"\" "+tr("is selected")+".");
}

void MainWindow::onMissionDeselected()
{
    editAction->setEnabled(false);
    delAction->setEnabled(false);
    missionLb->setText(tr("Nothing is selected."));
}

void MainWindow::onAboutToQuit()
{
    if (MLV->isChanged())
    {
        switch(QMessageBox::question(
                   this,
                   tr("Saving changes"),
                   tr("Would you like to save changes?"),
                   tr("Yes"), tr("No"),
                   0, 1))
        {
            case 0:
                onSaveAction();
                break;
            default:
                break;
        }
    }
}

void MainWindow::closeEvent(QCloseEvent *event)
{
    Q_UNUSED(event)
    onAboutToQuit();
}
