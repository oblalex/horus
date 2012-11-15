#ifndef MAIN_WINDOW_H
#define MAIN_WINDOW_H

#include <QMainWindow>
#include <QAbstractButton>
#include <QListWidgetItem>

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

    void closeEvent(QCloseEvent *event);
private slots:
    void onButtonClicked(QAbstractButton *button);

    void on_list_currentItemChanged(QListWidgetItem *current, QListWidgetItem *previous);

private:
    Ui::MainWindow *ui;
    void setWGeometry();
    void setSplitterPos();
    void addPages();
    void saveWGeometry();
};

#endif // MAIN_WINDOW_H
