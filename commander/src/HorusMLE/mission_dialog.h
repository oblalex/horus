#ifndef MISSION_DIALOG_H
#define MISSION_DIALOG_H

#include <QDialog>
#include <QList>
#include "map_list_view.h"

namespace Ui {
class MissionDialog;
}

class MissionDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit MissionDialog(MapListView* MLV, bool edit = false, QWidget *parent = 0);
    ~MissionDialog();
    
private slots:
    void on_pathBtn_clicked();
    void on_buttonBox_accepted();

private:
    Ui::MissionDialog *ui;
    MapListView* MLV;
};

#endif // MISSION_DIALOG_H
