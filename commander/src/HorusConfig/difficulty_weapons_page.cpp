#include "difficulty_weapons_page.h"
#include "ui_difficulty_weapons_page.h"

#define _RealisticGunnery           4096ULL
#define _LimitedAmmo                8192ULL
#define _LimitedFuel                16384ULL
#define _BombFuzes                  2147483648ULL
#define _RealisticTorpedoing        4294967296ULL
#define _RealisticMissilesVariation 274877906944ULL

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

quint64 DifficultyWeaponsPage::getDifficultyCode()
{
    quint64 code = 0;
    if (ui->RealisticGunnery->isChecked())              code += _RealisticGunnery;
    if (ui->LimitedAmmo->isChecked())                   code += _LimitedAmmo;
    if (ui->LimitedFuel->isChecked())                   code += _LimitedFuel;

    if (ui->BombFuzes->isChecked())                     code += _BombFuzes;
    if (ui->RealisticTorpedoing->isChecked())           code += _RealisticTorpedoing;
    if (ui->RealisticMissilesVariation->isChecked())    code += _RealisticMissilesVariation;

    return code;
}

void DifficultyWeaponsPage::setDifficultyCode(quint64 value)
{
    ui->RealisticGunnery->setChecked            (value & _RealisticGunnery);
    ui->LimitedAmmo->setChecked                 (value & _LimitedAmmo);
    ui->LimitedFuel->setChecked                 (value & _LimitedFuel);

    ui->BombFuzes->setChecked                   (value & _BombFuzes);
    ui->RealisticTorpedoing->setChecked         (value & _RealisticTorpedoing);
    ui->RealisticMissilesVariation->setChecked  (value & _RealisticMissilesVariation);
}
