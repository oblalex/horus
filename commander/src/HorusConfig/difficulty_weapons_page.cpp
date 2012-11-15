#include "difficulty_weapons_page.h"
#include "ui_difficulty_weapons_page.h"

#define _RealisticGunnery           4096
#define _LimitedAmmo                8192
#define _LimitedFuel                16384
#define _BombFuzes                  2147483648
#define _RealisticTorpedoing        4294967296
#define _RealisticMissilesVariation 274877906944

DifficultyWeaponsPage::DifficultyWeaponsPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::DifficultyWeaponsPage)
{
    ui->setupUi(this);
}

DifficultyWeaponsPage::~DifficultyWeaponsPage()
{
    delete ui;
}

qint64 DifficultyWeaponsPage::getDifficultyCode()
{
    qint64 code = 0;
    if (ui->RealisticGunnery->isChecked())              code += _RealisticGunnery;
    if (ui->LimitedAmmo->isChecked())                   code += _LimitedAmmo;
    if (ui->LimitedFuel->isChecked())                   code += _LimitedFuel;

    if (ui->BombFuzes->isChecked())                     code += _BombFuzes;
    if (ui->RealisticTorpedoing->isChecked())           code += _RealisticTorpedoing;
    if (ui->RealisticMissilesVariation->isChecked())    code += _RealisticMissilesVariation;

    return code;
}

void DifficultyWeaponsPage::setDifficultyCode(qint64 value)
{
    ui->RealisticGunnery->setChecked            (value & _RealisticGunnery);
    ui->LimitedAmmo->setChecked                 (value & _LimitedAmmo);
    ui->LimitedFuel->setChecked                 (value & _LimitedFuel);

    ui->BombFuzes->setChecked                   (value & _BombFuzes);
    ui->RealisticTorpedoing->setChecked         (value & _RealisticTorpedoing);
    ui->RealisticMissilesVariation->setChecked  (value & _RealisticMissilesVariation);
}
