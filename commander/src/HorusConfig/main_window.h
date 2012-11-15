#ifndef MAIN_WINDOW_H
#define MAIN_WINDOW_H

#include <QMainWindow>
#include <QAbstractButton>

#include "config_module.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow, public ConfigModule
{
    Q_OBJECT
    
public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    
    void save();
    void load();
    void loadDefaults();

private slots:
    void onButtonClicked(QAbstractButton *button);

private:
    Ui::MainWindow *ui;
    void setWGeometry();
    void setSplitterPos();
};

#endif // MAIN_WINDOW_H
