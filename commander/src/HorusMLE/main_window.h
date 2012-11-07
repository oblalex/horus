#ifndef MAIN_WINDOW_H
#define MAIN_WINDOW_H

#include <QMainWindow>
#include <QSpinBox>
#include <QLabel>
#include "mission_list_view.h"
#include "list_file_helper.h"

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
    void onLoadAction();
    void onClearAction();
    void onSaveAction();

    void onNewAction();
    void onEditAction();
    void onDelAction();

private:
    void createMenu();
    void createMainBar();
    void createNavBar();
    void createToolBar();
    void createToolActions();
    void createStatusBar();
    void createCentralWidget();

    bool isListEmpty();

    void onListLoaded();
    void onListNonLoaded();

    void onListEmpty();
    void onListNonEmpty();

    void redrawMissionsCount();

    Ui::MainWindow *ui;

    QAction *zoomInAction;
    QAction *zoomOutAction;

    QAction *newAction;
    QAction *editAction;
    QAction *delAction;

    QAction *loadAction;
    QAction *clearAction;
    QAction *saveAction;

    QAction *quitAction;

    MissionListView* MLV;

    QLabel* statLabel;

    ListFileHelper* lfHelper;
};

#endif // MAIN_WINDOW_H
