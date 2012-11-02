#ifndef MAIN_WINDOW_H
#define MAIN_WINDOW_H

#include <QMainWindow>
#include <QSpinBox>

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
    void createToolbar();
    void createActions();
    void createZoomSpin();
    void createCentralWidget();

    Ui::MainWindow *ui;

    QAction *selectAction;
    QAction *paneAction;
    QAction *zoomInAction;
    QAction *zoomOutAction;
    QAction *zoomSelectionAction;

    QAction *loadAction;
    QAction *saveAction;
    QAction *quitAction;

    QSpinBox* zoomSpin;
};

#endif // MAIN_WINDOW_H
