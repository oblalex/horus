#include "mission_dialog.h"
#include "ui_mission_dialog.h"
#include "d_mission.h"
#include <QFileDialog>

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

    QList<MissionElem*> lst = MLV->getMissions();

    ui->nextNoneCmb->addItem("");
    ui->nextRedCmb->addItem("");
    ui->nextBlueCmb->addItem("");

    foreach (MissionElem* me, lst)
    {
        ui->nextNoneCmb->addItem(me->data.name);
        ui->nextRedCmb->addItem(me->data.name);
        ui->nextBlueCmb->addItem(me->data.name);
    }

    MissionElem* missElem = MLV->getActive();

    if (edit)
    {
        setWindowTitle(tr("Editing mission"));
        ui->nameLe->setText(missElem->data.name);
        ui->pathLe->setText(missElem->data.path);
        ui->durationSpin->setValue(missElem->data.sDuration);
        ui->isCurrentChB->setChecked(missElem->isCurrent);

        ui->nextNoneCmb->setCurrentIndex(lst.indexOf(missElem->nextNone));
        ui->nextRedCmb->setCurrentIndex(lst.indexOf(missElem->nextRed));
        ui->nextBlueCmb->setCurrentIndex(lst.indexOf(missElem->nextBlue));
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
                    ? QDir::currentPath()
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
    me->isCurrent = ui->isCurrentChB->isChecked();
    MLV->checkCurrent(me);

    MissionElem* none = me->nextNone;
    MissionElem* red = me->nextRed;
    MissionElem* blue = me->nextBlue;

    me->rmDstEdges();

    if (none) none->update();
    if (red) red->update();
    if (blue) blue->update();

    me->nextNone = MLV->missionByName(ui->nextNoneCmb->currentText());
    me->nextRed = MLV->missionByName(ui->nextRedCmb->currentText());
    me->nextBlue = MLV->missionByName(ui->nextBlueCmb->currentText());

    me->updateDstEdges();

    accept();
}
