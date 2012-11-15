#ifndef GENERAL_PAGE_H
#define GENERAL_PAGE_H

#include <QWidget>

#include "named_page.h"
#include "config_module.h"

namespace Ui {
class GeneralPage;
}

class GeneralPage : public QWidget, public NamedPage, public ConfigModule
{
    Q_OBJECT
    
public:
    explicit GeneralPage(QWidget *parent = 0);
    ~GeneralPage();
    
    QString pageName();

    void save();
    void load();
    void loadDefaults();

private slots:
    void onLangChanged(int index);

private:
    Ui::GeneralPage *ui;

    void populateLangs();
    void selectLangInBox(QString lang);
};

#endif // GENERAL_PAGE_H
