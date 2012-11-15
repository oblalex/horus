#include "difficulty_var_page.h"
#include "ui_difficulty_var_page.h"

#define _Vulnerability                  32768
#define _RealisticPilotVulnerability    134217728
#define _HeadShake                      1024
#define _WindTurbulence                 1
#define _Clouds                         131072
#define _NoInstantSuccess               1048576
#define _TakeoffLanding                 128
#define _RealisticLanding               64
#define _RealisticNavigationInstruments 268435456

DifficultyVarPage::DifficultyVarPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::DifficultyVarPage)
{
    ui->setupUi(this);
}

DifficultyVarPage::~DifficultyVarPage()
{
    delete ui;
}

quint64 DifficultyVarPage::getDifficultyCode()
{
    quint64 code = 0;
    if (ui->Vulnerability->isChecked())                  code += _Vulnerability;
    if (ui->RealisticPilotVulnerability->isChecked())    code += _RealisticPilotVulnerability;
    if (ui->HeadShake->isChecked())                      code += _HeadShake;

    if (ui->WindTurbulence->isChecked())                 code += _WindTurbulence;
    if (ui->Clouds->isChecked())                         code += _Clouds;
    if (ui->NoInstantSuccess->isChecked())               code += _NoInstantSuccess;

    if (ui->TakeoffLanding->isChecked())                 code += _TakeoffLanding;
    if (ui->RealisticLanding->isChecked())               code += _RealisticLanding;
    if (ui->RealisticNavigationInstruments->isChecked()) code += _RealisticNavigationInstruments;

    return code;
}

void DifficultyVarPage::setDifficultyCode(quint64 value)
{
    ui->Vulnerability->setChecked                   (value & _Vulnerability);
    ui->RealisticPilotVulnerability->setChecked     (value & _RealisticPilotVulnerability);
    ui->HeadShake->setChecked                       (value & _HeadShake);

    ui->WindTurbulence->setChecked                  (value & _WindTurbulence);
    ui->Clouds->setChecked                          (value & _Clouds);
    ui->NoInstantSuccess->setChecked                (value & _NoInstantSuccess);

    ui->TakeoffLanding->setChecked                  (value & _TakeoffLanding);
    ui->RealisticLanding->setChecked                (value & _RealisticLanding);
    ui->RealisticNavigationInstruments->setChecked  (value & _RealisticNavigationInstruments);
}
