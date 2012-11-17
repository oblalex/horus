#include "general_page.h"
#include "ui_general_page.h"
#include "gs_cfg_grp.h"
#include "sys_cfg_key.h"
#include "settings.h"

#include <QVariant>
#include <QString>
#include <QMessageBox>

static QString KEY_SNAME = QString(GS_CFG_GRP_NET).append("/serverName");
static QString KEY_SDSCR = QString(GS_CFG_GRP_NET).append("/serverDescription");

GeneralPage::GeneralPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::GeneralPage)
{
    ui->setupUi(this);
    populateLangs();
}

GeneralPage::~GeneralPage()
{
    delete ui;
}

void GeneralPage::populateLangs()
{
    ui->langBox->addItem(QIcon(":/img/langEn.png"), tr("English"), SYS_CFG_LANG_EN);
    ui->langBox->addItem(QIcon(":/img/langRu.png"), tr("Russian"), SYS_CFG_LANG_RU);
}

void GeneralPage::selectLangInBox(QString lang)
{
    int id = ui->langBox->findData(lang);
    ui->langBox->setCurrentIndex((id<0)?0:id);

    connect(ui->langBox, SIGNAL(currentIndexChanged(int)), this, SLOT(onLangChanged(int)));
}

QString GeneralPage::pageName()
{
    return ui->pageName->text();
}

void GeneralPage::save()
{
    Settings::setServerValue(KEY_SNAME, ui->sNameLn->text());
    Settings::setServerValue(KEY_SDSCR, ui->sDescriptionLn->text());
    Settings::setHorusValue(QString(SYS_CFG_KEY_LANG),
                            ui->langBox->itemData(ui->langBox->currentIndex()).toString());

    saveChildren();
}

void GeneralPage::load()
{
    ui->sNameLn->setText(Settings::serverValue(KEY_SNAME, "").toString());
    ui->sDescriptionLn->setText(Settings::serverValue(KEY_SDSCR, "").toString());
    selectLangInBox(Settings::horusValue(QString(SYS_CFG_KEY_LANG), SYS_CFG_LANG_EN).toString());

    loadChildren();
}

void GeneralPage::loadDefaults()
{
    ui->sNameLn->setText(tr("My server"));
    ui->sDescriptionLn->setText(tr("No description"));
    selectLangInBox(SYS_CFG_LANG_EN);

    loadChildrenDefaults();
}

void GeneralPage::onLangChanged(int index)
{
    Q_UNUSED(index)
    QMessageBox::information(
                this,
                QObject::tr("Language changed"),
                QObject::tr("Restart application to apply new language."),
                QObject::tr("Ok"),
                0);
}
