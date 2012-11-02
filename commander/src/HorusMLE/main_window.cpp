#include "main_window.h"
#include "ui_main_window.h"
#include <QSplitter>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    createMenu();
    createNavBar();
    createToolBar();
    createCentralWidget();

    setWindowTitle(tr("Horus MLE"));
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
    //connect(loadAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    loadAction->setIcon(QIcon((":/img/load.png")));
    mainMenu->addAction(loadAction);

    saveAction = new QAction(tr("&Save"), this);
    //connect(saveAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    saveAction->setIcon(QIcon((":/img/save.png")));
    mainMenu->addAction(saveAction);

    mainMenu->addSeparator();

    quitAction = new QAction(tr("&Quit"), this);
    //connect(quitAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    quitAction->setIcon(QIcon((":/img/quit.png")));
    mainMenu->addAction(quitAction);
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
    //connect(newAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    newAction->setIcon(QIcon((":/img/new.png")));
    ui->toolBar->addAction(newAction);

    editAction = new QAction(tr("&Edit"), this);
    //connect(editAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    editAction->setIcon(QIcon((":/img/edit.png")));
    ui->toolBar->addAction(editAction);

    delAction = new QAction(tr("&Delete"), this);
    //connect(delAction, SIGNAL(triggered()), this, SLOT(showNormal()));
    delAction->setIcon(QIcon((":/img/delete.png")));
    ui->toolBar->addAction(delAction);
}

void MainWindow::createCentralWidget()
{
    MLV = new MapListView(this);
    setCentralWidget(MLV);
    MLV->setFocus();
}
