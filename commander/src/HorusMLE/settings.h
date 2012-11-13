#ifndef SETTINGS_H
#define SETTINGS_H

#include <QSettings>
#include <QRect>

#define SETTINGS_FILENAME "horusMLE.ini"
#define SETTINGS_DIR "./"
#define SETTINGS_PATH SETTINGS_DIR SETTINGS_FILENAME

#define LANG_RU "ru"
#define LANG_EN "en"

class Settings
{
public:
    Settings(bool create = true);
    void save();
    bool isLoaded();

    QRect winGeom;
    QString lang;
private:
    void load();

    void saveWGeom();
    void loadWGeom();

    QSettings* settings;
    bool loaded;
};

#endif // SETTINGS_H
