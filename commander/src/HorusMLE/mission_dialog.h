#ifndef MISSION_DIALOG_H
#define MISSION_DIALOG_H

#include <QDialog>
#include <QList>
#include "mission_list_view.h"

namespace Ui {
class MissionDialog;
}

class MissionDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit MissionDialog(MissionListView* MLV, bool edit = false, QWidget *parent = 0);
    ~MissionDialog();
    
private slots:
    void on_pathBtn_clicked();
    void on_buttonBox_accepted();

private:
    Ui::MissionDialog *ui;
    MissionListView* MLV;
};

#endif // MISSION_DIALOG_H
