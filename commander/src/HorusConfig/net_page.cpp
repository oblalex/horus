#include "net_page.h"
#include "ui_net_page.h"
#include "settings.h"
#include "gs_cfg_grp.h"

static QString KEY_SKIN                     = QString(GS_CFG_GRP_NET).append("/SkinDownload");
static QString KEY_CHECK_CLIENT_TIME_SPEED  = QString(GS_CFG_GRP_NET).append("/checkClientTimeSpeed");
static QString KEY_CHECK_SERVER_TIME_SPEED  = QString(GS_CFG_GRP_NET).append("/checkServerTimeSpeed");
static QString KEY_CHECK_TIME_SPEED_DIFF    = QString(GS_CFG_GRP_NET).append("/checkTimeSpeedDifferense");
static QString KEY_CHECK_TIME_SPEED_INTV    = QString(GS_CFG_GRP_NET).append("/checkTimeSpeedInterval");
static QString KEY_LOCAL_PORT               = QString(GS_CFG_GRP_NET).append("/localPort");
static QString KEY_CHANNELS                 = QString(GS_CFG_GRP_NET).append("/serverChannels");

static QString KEY_CHEAT_WARN_DELAY         = QString(GS_CFG_GRP_MAX_LAG).append("/cheaterWarningDelay");
static QString KEY_CHEAT_WARN_NUM           = QString(GS_CFG_GRP_MAX_LAG).append("/cheaterWarningNum");
static QString KEY_CHEAT_FAR_MAX_LAG_TIME   = QString(GS_CFG_GRP_MAX_LAG).append("/farMaxLagTime");
static QString KEY_CHEAT_NEAR_MAX_LAG_TIME  = QString(GS_CFG_GRP_MAX_LAG).append("/nearMaxLagTime");

NetPage::NetPage(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::NetPage)
{
    ui->setupUi(this);
}

NetPage::~NetPage()
{
    delete ui;
}

QString NetPage::pageName()
{
    return ui->pageName->text();
}

void NetPage::save()
{
    Settings::setServerValue(KEY_SKIN, (ui->SkinDownload->isChecked()?1:0));
    Settings::setServerValue(KEY_CHECK_CLIENT_TIME_SPEED, (ui->CheckClientTimeSpeed->isChecked()?1:0));
    Settings::setServerValue(KEY_CHECK_SERVER_TIME_SPEED, (ui->CheckServerTimeSpeed->isChecked()?1:0));

    Settings::setServerValue(KEY_CHECK_TIME_SPEED_DIFF, ui->CheckTimeSpeedDifferense->value());
    Settings::setServerValue(KEY_CHECK_TIME_SPEED_INTV, ui->CheckTimeSpeedInterval->value());

    Settings::setServerValue(KEY_CHANNELS, ui->ChannelsSlider->value());
    Settings::setServerValue(KEY_LOCAL_PORT, ui->LocalPort->value());

    Settings::setServerValue(KEY_CHEAT_WARN_DELAY, ui->CheaterWarningDelay->value());
    Settings::setServerValue(KEY_CHEAT_WARN_NUM, ui->CheaterWarningNum->value());
    Settings::setServerValue(KEY_CHEAT_FAR_MAX_LAG_TIME, ui->FarMaxLagTime->value());
    Settings::setServerValue(KEY_CHEAT_NEAR_MAX_LAG_TIME, ui->NearMaxLagTime->value());

    saveChildren();
}

void NetPage::load()
{
    ui->SkinDownload->setChecked(Settings::serverValue(KEY_SKIN, "0").toInt() > 0);
    ui->CheckClientTimeSpeed->setChecked(Settings::serverValue(KEY_CHECK_CLIENT_TIME_SPEED, "0").toInt() > 0);
    ui->CheckServerTimeSpeed->setChecked(Settings::serverValue(KEY_CHECK_SERVER_TIME_SPEED, "0").toInt() > 0);

    ui->CheckTimeSpeedDifferense->setValue(Settings::serverValue(KEY_CHECK_TIME_SPEED_DIFF, "0.3").toDouble());
    ui->CheckTimeSpeedInterval->setValue(Settings::serverValue(KEY_CHECK_TIME_SPEED_INTV, "10.0").toDouble());

    ui->ChannelsSlider->setValue(Settings::serverValue(KEY_CHANNELS, "32").toInt());
    ui->LocalPort->setValue(Settings::serverValue(KEY_LOCAL_PORT, "21000").toInt());

    ui->CheaterWarningDelay->setValue(Settings::serverValue(KEY_CHEAT_WARN_DELAY, "10.0").toDouble());
    ui->CheaterWarningNum->setValue(Settings::serverValue(KEY_CHEAT_WARN_NUM, "3").toInt());
    ui->FarMaxLagTime->setValue(Settings::serverValue(KEY_CHEAT_FAR_MAX_LAG_TIME, "2.0").toDouble());
    ui->NearMaxLagTime->setValue(Settings::serverValue(KEY_CHEAT_NEAR_MAX_LAG_TIME, "1.0").toDouble());

    loadChildren();
}

void NetPage::loadDefaults()
{
    ui->SkinDownload->setChecked(true);
    ui->CheckClientTimeSpeed->setChecked(true);
    ui->CheckServerTimeSpeed->setChecked(true);

    ui->CheckTimeSpeedDifferense->setValue(0.3d);
    ui->CheckTimeSpeedInterval->setValue(10.0d);

    ui->ChannelsSlider->setValue(32);
    ui->LocalPort->setValue(21000);

    ui->CheaterWarningDelay->setValue(10.0d);
    ui->CheaterWarningNum->setValue(3);

    ui->FarMaxLagTime->setValue(2.0d);
    ui->NearMaxLagTime->setValue(1.0d);

    loadChildrenDefaults();
}

void NetPage::on_ChannelsSlider_valueChanged(int value)
{
    ui->ChannelsLb->setText(QString::number(value));
}
