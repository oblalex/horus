#ifndef SETTINGS_H
#define SETTINGS_H

#include <QVariant>

#define LANG_EN "en"
#define LANG_RU "ru"

#define GRP_GENERAL "GNRL"
#define KEY_LANG GRP_GENERAL "/lang"

class Settings
{
public:
    static void init();
    static void save();

    static QString getLangCode();

    static const QVariant horusValue(const QString &key, const QVariant &defaultValue = QVariant());
    static const QVariant serverValue(const QString &key, const QVariant &defaultValue = QVariant());

    static void setHorusValue(const QString &key, const QVariant value);
    static void setServerValue(const QString &key, const QVariant value);
};

#endif // SETTINGS_H
