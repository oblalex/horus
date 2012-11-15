#include "difficulty_view_page.h"
#include "ui_difficulty_view_page.h"

#define _NoOutSideViews     512
#define _NoSpeedBar         4194304
#define _NoPadlock          65536
#define _CockpitAlwaysOn    256
#define _NoSelfView         137438953472
#define _NoFoeView          8589934592
#define _NoFriendlyView     17179869184
#define _NoPlanesView       68719476736
#define _NoACarrierView     34359738368

DifficultyViewPage::DifficultyViewPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::DifficultyViewPage)
{
    ui->setupUi(this);
}

DifficultyViewPage::~DifficultyViewPage()
{
    delete ui;
}

qint64 DifficultyViewPage::getDifficultyCode()
{
    qint64 code = 0;

    if (ui->NoOutSideViews->isChecked())    code += _NoOutSideViews;
    if (ui->NoSpeedBar->isChecked())        code += _NoSpeedBar;
    if (ui->NoPadlock->isChecked())         code += _NoPadlock;

    if (ui->CockpitAlwaysOn->isChecked())   code += _CockpitAlwaysOn;
    if (ui->NoSelfView->isChecked())        code += _NoSelfView;
    if (ui->NoFoeView->isChecked())         code += _NoFoeView;

    if (ui->NoFriendlyView->isChecked())    code += _NoFriendlyView;
    if (ui->NoPlanesView->isChecked())      code += _NoPlanesView;
    if (ui->NoACarrierView->isChecked())    code += _NoACarrierView;

    return code;
}

void DifficultyViewPage::setDifficultyCode(qint64 value)
{
    ui->NoOutSideViews->setChecked  (value & _NoOutSideViews);
    ui->NoSpeedBar->setChecked      (value & _NoSpeedBar);
    ui->NoPadlock->setChecked       (value & _NoPadlock);

    ui->CockpitAlwaysOn->setChecked (value & _CockpitAlwaysOn);
    ui->NoSelfView->setChecked      (value & _NoSelfView);
    ui->NoFoeView->setChecked       (value & _NoFoeView);

    ui->NoFriendlyView->setChecked  (value & _NoFriendlyView);
    ui->NoPlanesView->setChecked    (value & _NoPlanesView);
    ui->NoACarrierView->setChecked  (value & _NoACarrierView);
}

void DifficultyViewPage::on_NoOutSideViews_toggled(bool checked)
{
    if (checked)
    {
        ui->NoSelfView->setChecked(true);
        ui->NoFoeView->setChecked(true);
        ui->NoFriendlyView->setChecked(true);
        ui->NoPlanesView->setChecked(true);
        ui->NoACarrierView->setChecked(true);

        ui->NoSelfView->setEnabled(false);
        ui->NoFoeView->setEnabled(false);
        ui->NoFriendlyView->setEnabled(false);
        ui->NoPlanesView->setEnabled(false);
        ui->NoACarrierView->setEnabled(false);
    } else {
        ui->NoSelfView->setEnabled(true);
        ui->NoFoeView->setEnabled(true);
        ui->NoFriendlyView->setEnabled(true);
        ui->NoPlanesView->setEnabled(true);
        ui->NoACarrierView->setEnabled(true);
    }
}
