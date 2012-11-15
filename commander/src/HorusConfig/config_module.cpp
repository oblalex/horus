#include "config_module.h"

ConfigModule::ConfigModule()
{
}

void ConfigModule::addChild(ConfigModule *child)
{
    if (children.contains(child)) return;
    children << child;
}

void ConfigModule::rmChild(ConfigModule *child)
{
    if (children.contains(child)==false) return;
    children.removeOne(child);
}

void ConfigModule::saveChildren()
{
    foreach (ConfigModule * ch, children)
        ch->save();
}

void ConfigModule::loadChildren()
{
    foreach (ConfigModule * ch, children)
        ch->load();
}

void ConfigModule::loadChildrenDefaults()
{
    foreach (ConfigModule * ch, children)
        ch->loadDefaults();
}
