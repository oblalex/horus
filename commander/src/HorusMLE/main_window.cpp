#include "main_window.h"
#include "ui_main_window.h"
#include "mission_dialog.h"
#include <QSplitter>

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
    mainMenu->addAction(loadAction);

    clearAction = new QAction(tr("&Clear"), this);
    connect(clearAction, SIGNAL(triggered()), this, SLOT(onClearAction()));
    clearAction->setIcon(QIcon((":/img/clear.png")));
    mainMenu->addAction(clearAction);

    saveAction = new QAction(tr("&Save"), this);
    connect(saveAction, SIGNAL(triggered()), this, SLOT(onSaveAction()));
    saveAction->setIcon(QIcon((":/img/save.png")));
    mainMenu->addAction(saveAction);

    mainMenu->addSeparator();

    quitAction = new QAction(tr("&Quit"), this);
    connect(quitAction, SIGNAL(triggered()), this, SLOT(onQuitAction()));
    quitAction->setIcon(QIcon((":/img/quit.png")));
    mainMenu->addAction(quitAction);
}

void MainWindow::createMainBar()
{
    ui->mainBar->addAction(loadAction);
    ui->mainBar->addAction(clearAction);
    ui->mainBar->addAction(saveAction);
}

void MainWindow::createNavBar()
{
    ui->navBar->setIconSize(QSize(18, 18));

    createNavActions();
    createZoomSpin();
}

void MainWindow::createNavActions()
{
    selectAction = new QAction(tr("&Select"), this);
    //connect(selectAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    selectAction->setIcon(QIcon((":/img/select.png")));

    ui->navBar->addAction(selectAction);

    paneAction = new QAction(tr("&Pane"), this);
    //connect(paneAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    paneAction->setIcon(QIcon((":/img/pane.png")));
    ui->navBar->addAction(paneAction);

    zoomInAction = new QAction(tr("Zoom In"), this);
    //connect(zoomInAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    zoomInAction->setIcon(QIcon((":/img/zoom_in.png")));
    ui->navBar->addAction(zoomInAction);

    zoomOutAction = new QAction(tr("Zoom Out"), this);
    //connect(zoomOutAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    zoomOutAction->setIcon(QIcon((":/img/zoom_out.png")));
    ui->navBar->addAction(zoomOutAction);

    zoomSelectionAction = new QAction(tr("&Zoom Selection"), this);
    //connect(zoomSelectionAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    zoomSelectionAction->setIcon(QIcon((":/img/zoom_selection.png")));
    ui->navBar->addAction(zoomSelectionAction);
}

void MainWindow::createZoomSpin()
{
    zoomSpin = new QSpinBox(this);
    QRect rect = zoomSpin->geometry();
    rect.setWidth(70);
    zoomSpin->setGeometry(rect);
    zoomSpin->setMaximum(1000);
    zoomSpin->setMinimum(10);
    zoomSpin->setSingleStep(10);
    zoomSpin->setValue(100);
    zoomSpin->setSuffix(" %");
    ui->navBar->addWidget(zoomSpin);
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
    ui->toolBar->addAction(newAction);

    editAction = new QAction(tr("&Edit"), this);
    connect(editAction, SIGNAL(triggered()), this, SLOT(onEditAction()));
    editAction->setIcon(QIcon((":/img/edit.png")));
    ui->toolBar->addAction(editAction);

    delAction = new QAction(tr("&Delete"), this);
    //connect(delAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    delAction->setIcon(QIcon((":/img/delete.png")));
    ui->toolBar->addAction(delAction);
}

void MainWindow::createStatusBar()
{
    statLabel = new QLabel(this);
    statLabel->setAlignment(Qt::AlignLeft | Qt::AlignVCenter);
    statLabel->setMargin(2);
    ui->statusBar->addWidget(statLabel);
}

void MainWindow::createCentralWidget()
{
    MLV = new MissionListView(this);
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

    statLabel->clear();

    if (isListEmpty())
        onListEmpty();
    else
        onListNonEmpty();
}

void MainWindow::onListNonLoaded()
{
    statLabel->setText(tr("Mission list is not loaded"));
    saveAction->setEnabled(false);

    onListEmpty();
}

void MainWindow::onListEmpty()
{
    if (statLabel->text().isEmpty())
        statLabel->setText(tr("Mission list is empty"));

    clearAction->setEnabled(false);

    selectAction->setEnabled(false);
    paneAction->setEnabled(false);
    zoomInAction->setEnabled(false);
    zoomOutAction->setEnabled(false);
    zoomSelectionAction->setEnabled(false);
    zoomSpin->setEnabled(false);

    editAction->setEnabled(false);
    delAction->setEnabled(false);
}

void MainWindow::onListNonEmpty()
{
    redrawMissionsCount();

    clearAction->setEnabled(true);

    selectAction->setEnabled(true);
    paneAction->setEnabled(true);
    zoomInAction->setEnabled(true);
    zoomOutAction->setEnabled(true);
    zoomSelectionAction->setEnabled(true);
    zoomSpin->setEnabled(true);

    editAction->setEnabled(true);
    delAction->setEnabled(true);
}

void MainWindow::redrawMissionsCount()
{
    statLabel->setText(tr("Missions: ")+QString::number(MLV->missionsCount()));
}

void MainWindow::onQuitAction()
{
    qApp->quit();
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
    foreach (MissionElem* me, MLV->getMissions())
    {
        MLV->scene->removeItem(me);
        delete me;
    }

    MLV->missionsClear();
    MLV->scene->update();
    redrawMissionsCount();
}

void MainWindow::onSaveAction()
{
    lfHelper->saveFromView();
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
        me->update();
}
