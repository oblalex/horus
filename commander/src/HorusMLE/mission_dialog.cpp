#include "mission_dialog.h"
#include "ui_mission_dialog.h"
#include "d_mission.h"
#include <QFileDialog>

#include "iostream"
using namespace std;

MissionDialog::MissionDialog(MissionListView* MLV, bool edit, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::MissionDialog),
    MLV(MLV)
{
    ui->setupUi(this);

    ui->nameLb->setText(tr("Name:"));
    ui->pathLb->setText(tr("Path:"));
    ui->pathBtn->setText(tr("Browse..."));
    ui->durationLb->setText(tr("Duration (s):"));
    ui->isCurrentChB->setText(tr("Is current"));
    ui->nextNoneLb->setText(tr("Next none:"));
    ui->nextRedLb->setText(tr("Next red:"));
    ui->nextBlueLb->setText(tr("Next blue:"));

    QList<QString> lst;
    lst << "";

    foreach (MissionElem* me, MLV->getMissions())
        lst << QString::fromUtf8(me->data.name);

    foreach (QString name, lst)
    {
        ui->nextNoneCmb->addItem(name);
        ui->nextRedCmb->addItem(name);
        ui->nextBlueCmb->addItem(name);
    }

    MissionElem* missElem = MLV->getActive();

    if (edit)
    {
        setWindowTitle(tr("Editing mission"));
        ui->nameLe->setText(QString::fromUtf8(missElem->data.name));
        ui->pathLe->setText(QString::fromUtf8(missElem->data.path));
        ui->durationSpin->setValue(missElem->data.sDuration);
        ui->isCurrentChB->setChecked(missElem->isCurrent());

        if (missElem->nextNone)
            ui->nextNoneCmb->setCurrentIndex(lst.indexOf(QString::fromUtf8(missElem->nextNone->data.name)));
        if (missElem->nextRed)
            ui->nextRedCmb->setCurrentIndex(lst.indexOf(QString::fromUtf8(missElem->nextRed->data.name)));
        if (missElem->nextBlue)
            ui->nextBlueCmb->setCurrentIndex(lst.indexOf(QString::fromUtf8(missElem->nextBlue->data.name)));

    } else {
        setWindowTitle(tr("Adding new mission"));
        ui->durationSpin->setValue(QString(DEFAULT_MISSION_DURATION).toInt());

        ui->nextNoneCmb->setCurrentIndex(0);
        ui->nextRedCmb->setCurrentIndex(0);
        ui->nextBlueCmb->setCurrentIndex(0);
    }
}

MissionDialog::~MissionDialog()
{
    delete ui;
}

void MissionDialog::on_pathBtn_clicked()
{
    QString misDir = "Missions";
    QString misPath = "../" + misDir + "/";
    QString startDir;
    startDir = (QDir(misPath).exists())
               ? ((ui->pathLe->text().isEmpty())
                    ? misPath
                    : misPath+ui->pathLe->text())
               : QDir::currentPath();

    QString file = QFileDialog::getOpenFileName(this, tr("Find mission"), QDir(startDir).absolutePath());

    if (!file.isEmpty())
    {
        int pos = file.indexOf(misDir);
        if (pos>=0)
        {
            pos = pos+misDir.length()+1;
            ui->pathLe->setText(file.mid(pos, file.length()-pos));
        }
    }
}

void MissionDialog::on_buttonBox_accepted()
{
    QString name = ui->nameLe->text();
    QString path = ui->pathLe->text();

    if (name.isEmpty() || path.isEmpty()) return;

    MissionElem* me = MLV->getActive();

    me->setName(&name);
    me->setPath(&path);

    me->data.sDuration = ui->durationSpin->value();
    me->setCurrent(ui->isCurrentChB->isChecked());
    MLV->checkCurrent(me);

    MissionElem* none = me->nextNone;
    MissionElem* red = me->nextRed;
    MissionElem* blue = me->nextBlue;

    me->rmDstEdges();
    me->nextNone = MLV->missionByName(ui->nextNoneCmb->currentText());
    me->nextRed = MLV->missionByName(ui->nextRedCmb->currentText());
    me->nextBlue = MLV->missionByName(ui->nextBlueCmb->currentText());

    if (none) {none->updateRadius(); none->updateEdges(); none->update();}
    if (red) {red->updateRadius(); red->updateEdges(); red->update();}
    if (blue) {blue->updateRadius(); blue->updateEdges(); blue->update();}

    me->updateDstEdges();
    me->updateEdges();
    me->update();
    me->updateToolTip();

    accept();
}
