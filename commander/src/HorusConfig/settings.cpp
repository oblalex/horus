#include "settings.h"

#include <QSettings>
#include <QFile>
#include <QMessageBox>
#include <QTextCodec>
#include <stdlib.h>

static QSettings* settingsHorus;
static QSettings* settingsServer;

void Settings::init()
{
    QString hPath = "./config.ini";
    QString sPath = "../confs.ini";

    QFile hFile(hPath);
    if (hFile.open(QIODevice::ReadWrite))
        hFile.close();
    else {
        QMessageBox::critical(
                    NULL,
                    QObject::tr("File cannot be created"),
                    QObject::tr("Horus config file cannot be created."),
                    QObject::tr("Ok"),
                    0);
        exit(EXIT_FAILURE);
    }

    QFile sFile(sPath);
    if (sFile.open(QIODevice::ReadOnly))
        sFile.close();
    else {
        QMessageBox::critical(
                    NULL,
                    QObject::tr("File cannot be opened"),
                    QObject::tr("Server config file cannot be opened."),
                    QObject::tr("Ok"),
                    0);
        exit(EXIT_FAILURE);
    }

    settingsHorus = new QSettings(hPath, QSettings::IniFormat);
    settingsServer = new QSettings(sPath, QSettings::IniFormat);

    settingsHorus->setIniCodec("utf8");
    settingsServer->setIniCodec("utf8");
}

void Settings::save()
{
    settingsHorus->sync();
    settingsServer->sync();
}

QString Settings::getLangCode()
{
    return horusValue(QString(KEY_LANG), LANG_EN).toString();
}

const QVariant Settings::horusValue(const QString &key, const QVariant &defaultValue)
{
    return settingsHorus->value(key, defaultValue);
}

const QVariant Settings::serverValue(const QString &key, const QVariant &defaultValue)
{
    return settingsServer->value(key, defaultValue);
}

void Settings::setHorusValue(const QString &key, const QVariant value)
{
    settingsHorus->setValue(key, value);
}

void Settings::setServerValue(const QString &key, const QVariant value)
{
   settingsServer->setValue(key, value);
}
