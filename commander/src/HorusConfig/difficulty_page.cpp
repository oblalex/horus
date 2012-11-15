#include "difficulty_page.h"
#include "ui_difficulty_page.h"
#include "settings.h"
#include "gs_cfg_sections.h"

#include "difficulty_flight_model_page.h"
#include "difficulty_weapons_page.h"
#include "difficulty_view_page.h"
#include "difficulty_map_icons_page.h"

static QString KEY_DIFFICULTY = QString(GS_CFG_GRP_NET).append("/difficulty");

DifficultyPage::DifficultyPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::DifficultyPage)
{
    ui->setupUi(this);
    setSplitterPos();
    addPages();
}

DifficultyPage::~DifficultyPage()
{
    delete ui;
}

void DifficultyPage::setSplitterPos()
{
    QList<int> sizes;
    int listW = qMin((width()*30)/100, 180);
    sizes << listW;
    sizes << width()-listW;
    ui->splitter->setSizes(sizes);
}

void DifficultyPage::addPages()
{
    DifficultyFlightModelPage* fm = new DifficultyFlightModelPage;
    new QListWidgetItem(
                tr("Flight model"),
                ui->list,
                QListWidgetItem::UserType);
    ui->stack->addWidget(fm);
    subpages << fm;

    DifficultyWeaponsPage* wpn = new DifficultyWeaponsPage;
    new QListWidgetItem(
                tr("Weapons"),
                ui->list,
                QListWidgetItem::UserType);
    ui->stack->addWidget(wpn);
    subpages << wpn;

    DifficultyViewPage* vw = new DifficultyViewPage;
    new QListWidgetItem(
                tr("View"),
                ui->list,
                QListWidgetItem::UserType);
    ui->stack->addWidget(vw);
    subpages << vw;

    DifficultyMapIconsPage* mi = new DifficultyMapIconsPage;
    new QListWidgetItem(
                tr("Map & icons"),
                ui->list,
                QListWidgetItem::UserType);
    ui->stack->addWidget(mi);
    subpages << mi;

    ui->stack->setCurrentIndex(0);
    ui->list->setCurrentRow(0);
}

void DifficultyPage::loadCode(quint64 code)
{
    foreach (DifficultySubpage* sp, subpages)
        sp->setDifficultyCode(code);
}

QString DifficultyPage::pageName()
{
    return ui->pageName->text();
}

void DifficultyPage::save()
{
    quint64 code = 0;

    foreach (DifficultySubpage* sp, subpages)
        code += sp->getDifficultyCode();

    Settings::setServerValue(KEY_DIFFICULTY, code);
    saveChildren();
}

void DifficultyPage::load()
{
    quint64 code = Settings::serverValue(KEY_DIFFICULTY, "0").toUInt();
    loadCode(code);
    loadChildren();
}

void DifficultyPage::loadDefaults()
{
    loadCode(0);
    loadChildrenDefaults();
}

void DifficultyPage::on_list_currentItemChanged(QListWidgetItem *current, QListWidgetItem *previous)
{
    if (!current)
        current = previous;

    ui->stack->setCurrentIndex(ui->list->row(current));
}
