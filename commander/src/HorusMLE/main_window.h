#ifndef MAIN_WINDOW_H
#define MAIN_WINDOW_H

#include <QMainWindow>
#include <QSpinBox>
#include <QLabel>
#include "map_list_view.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT
    
public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    
private slots:
    void onQuitAction();

private:
    void createMenu();
    void createMainBar();
    void createNavBar();
    void createNavActions();
    void createZoomSpin();
    void createToolBar();
    void createToolActions();
    void createStatusBar();
    void createCentralWidget();

    bool isListEmpty();

    void onListLoaded();
    void onListNonLoaded();

    void onListEmpty();
    void onListNonEmpty();

    Ui::MainWindow *ui;

    QAction *selectAction;
    QAction *paneAction;
    QAction *zoomInAction;
    QAction *zoomOutAction;
    QAction *zoomSelectionAction;

    QAction *newAction;
    QAction *editAction;
    QAction *delAction;

    QAction *loadAction;
    QAction *clearAction;
    QAction *saveAction;

    QAction *quitAction;

    QSpinBox* zoomSpin;
    MapListView* MLV;

    QLabel* statLabel;
};

#endif // MAIN_WINDOW_H
