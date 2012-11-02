#ifndef MAIN_WINDOW_H
#define MAIN_WINDOW_H

#include <QMainWindow>
#include <QSpinBox>
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
    
private:
    void createMenu();
    void createMainBar();
    void createNavBar();
    void createNavActions();
    void createZoomSpin();
    void createToolBar();
    void createToolActions();
    void createCentralWidget();

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
};

#endif // MAIN_WINDOW_H
