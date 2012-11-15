#include "difficulty_flight_model_page.h"
#include "ui_difficulty_flight_model_page.h"

#define _SeparateEStart     524288
#define _ComplexEManagement 8388608
#define _EngineOverheat     16
#define _TorqueGyroEffects  32
#define _FlutterEffect      2
#define _StallSpins         4
#define _BlackoutsRedouts   8
#define _GLimits            67108864
#define _Reliability        33554432

DifficultyFlightModelPage::DifficultyFlightModelPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::DifficultyFlightModelPage)
{
    ui->setupUi(this);
}

DifficultyFlightModelPage::~DifficultyFlightModelPage()
{
    delete ui;
}

quint64 DifficultyFlightModelPage::getDifficultyCode()
{
    quint64 code = 0;
    if (ui->SeparateEStart->isChecked())     code += _SeparateEStart;
    if (ui->ComplexEManagement->isChecked()) code += _ComplexEManagement;
    if (ui->EngineOverheat->isChecked())     code += _EngineOverheat;

    if (ui->TorqueGyroEffects->isChecked())  code += _TorqueGyroEffects;
    if (ui->FlutterEffect->isChecked())      code += _FlutterEffect;
    if (ui->StallSpins->isChecked())         code += _StallSpins;

    if (ui->BlackoutsRedouts->isChecked())   code += _BlackoutsRedouts;
    if (ui->GLimits->isChecked())            code += _GLimits;
    if (ui->Reliability->isChecked())        code += _Reliability;

    return code;
}

void DifficultyFlightModelPage::setDifficultyCode(quint64 value)
{
    ui->SeparateEStart->setChecked      (value & _SeparateEStart);
    ui->ComplexEManagement->setChecked  (value & _ComplexEManagement);
    ui->EngineOverheat->setChecked      (value & _EngineOverheat);

    ui->TorqueGyroEffects->setChecked   (value & _TorqueGyroEffects);
    ui->FlutterEffect->setChecked       (value & _FlutterEffect);
    ui->StallSpins->setChecked          (value & _StallSpins);

    ui->BlackoutsRedouts->setChecked    (value & _BlackoutsRedouts);
    ui->GLimits->setChecked             (value & _GLimits);
    ui->Reliability->setChecked         (value & _Reliability);
}
