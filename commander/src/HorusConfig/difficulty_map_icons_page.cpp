#include "difficulty_map_icons_page.h"
#include "ui_difficulty_map_icons_page.h"

#define _NoMapIcons         262144ULL
#define _NoPlayerIcon       536870912ULL
#define _NoFogOfWarIcons    1073741824ULL
#define _NoMinimapPath      2097152ULL
#define _NoIcons            2048ULL

DifficultyMapIconsPage::DifficultyMapIconsPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::DifficultyMapIconsPage)
{
    ui->setupUi(this);
}

DifficultyMapIconsPage::~DifficultyMapIconsPage()
{
    delete ui;
}

quint64 DifficultyMapIconsPage::getDifficultyCode()
{
    quint64 code = 0;

    if (ui->NoPlayerIcon->isChecked())      code += _NoPlayerIcon;
    if (ui->NoFogOfWarIcons->isChecked())   code += _NoFogOfWarIcons;
    if (ui->NoMinimapPath->isChecked())     code += _NoMinimapPath;
    if (ui->NoIcons->isChecked())           code += _NoIcons;
    if (ui->NoMapIcons->isChecked())        code += _NoMapIcons;

    return code;
}

void DifficultyMapIconsPage::setDifficultyCode(quint64 value)
{
    ui->NoPlayerIcon->setChecked    (value & _NoPlayerIcon);
    ui->NoFogOfWarIcons->setChecked (value & _NoFogOfWarIcons);
    ui->NoMinimapPath->setChecked   (value & _NoMinimapPath);
    ui->NoIcons->setChecked         (value & _NoIcons);
    ui->NoMapIcons->setChecked      (value & _NoMapIcons);
}

void DifficultyMapIconsPage::on_NoMapIcons_toggled(bool checked)
{
    if (checked)
    {
        ui->NoFogOfWarIcons->setEnabled(true);
    } else {
        ui->NoFogOfWarIcons->setChecked(true);
        ui->NoFogOfWarIcons->setEnabled(false);
    }
}
