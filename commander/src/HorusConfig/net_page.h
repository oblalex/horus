#ifndef NET_PAGE_H
#define NET_PAGE_H

#include <QWidget>

#include "named_page.h"
#include "config_module.h"

namespace Ui {
class NetPage;
}

class NetPage : public QWidget, public NamedPage, public ConfigModule
{
    Q_OBJECT
    
public:
    explicit NetPage(QWidget *parent = 0);
    ~NetPage();
    
    QString pageName();

    void save();
    void load();
    void loadDefaults();

private slots:
    void on_ChannelsSlider_valueChanged(int value);

private:
    Ui::NetPage *ui;
};

#endif // NET_PAGE_H
