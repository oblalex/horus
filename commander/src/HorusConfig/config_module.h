#ifndef CONFIG_MODULE_H
#define CONFIG_MODULE_H

#include <QList>

class ConfigModule;

class ConfigModule
{
public:
    ConfigModule();

    void addChild(ConfigModule* child);
    void rmChild(ConfigModule* child);

    virtual void save() = 0;
    virtual void load() = 0;
    virtual void loadDefaults() = 0;

protected:
    void saveChildren();
    void loadChildren();
    void loadChildrenDefaults();

private:
    QList<ConfigModule*> children;
};

#endif // CONFIG_MODULE_H
