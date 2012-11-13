#include "settings.h"

#include <QVariant>
#include <QFile>

#define WND_GRP_NAME "WND"

#define WND_X_KEY   WND_GRP_NAME "/x"
#define WND_Y_KEY   WND_GRP_NAME "/y"
#define WND_W_KEY   WND_GRP_NAME "/w"
#define WND_H_KEY   WND_GRP_NAME "/h"

#define GENERAL_GRP_NAME "GNRL"

#define GENERAL_LANG_KEY GENERAL_GRP_NAME "/lang"

Settings::Settings(bool create)
{
    loaded = false;
    QString fPath = SETTINGS_PATH;
    QFile f(fPath);

    bool existed = f.exists();

    if (existed==false)
    {
        if (create && f.open(QIODevice::WriteOnly))
            f.close();
        else
            return;
    }

    settings = new QSettings(fPath, QSettings::IniFormat);
    if (existed) load();
}

void Settings::load()
{
    loadWGeom();
    lang = settings->value(GENERAL_LANG_KEY, "").toString();
    if (lang.isEmpty()) lang = LANG_EN;
    loaded = true;
}

void Settings::loadWGeom()
{
    int x, y, w, h;
    x = settings->value(WND_X_KEY, 0).toInt();
    y = settings->value(WND_Y_KEY, 0).toInt();
    w = settings->value(WND_W_KEY, 400).toInt();
    h = settings->value(WND_H_KEY, 400).toInt();

    winGeom.setX(x);
    winGeom.setY(y);
    winGeom.setWidth(w);
    winGeom.setHeight(h);
}

void Settings::save()
{
    saveWGeom();
    settings->setValue(GENERAL_LANG_KEY, lang);
    settings->sync();
}

void Settings::saveWGeom()
{
    settings->setValue(WND_X_KEY, winGeom.x());
    settings->setValue(WND_Y_KEY, winGeom.y());
    settings->setValue(WND_W_KEY, winGeom.width());
    settings->setValue(WND_H_KEY, winGeom.height());
}

bool Settings::isLoaded()
{
    return loaded;
}
