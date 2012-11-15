#ifndef DIFFICULTY_PAGE_H
#define DIFFICULTY_PAGE_H

#include <QWidget>
#include <QList>
#include "difficulty_subpage.h"

#include "named_page.h"
#include "config_module.h"

namespace Ui {
class DifficultyPage;
}

class DifficultyPage : public QWidget, public NamedPage, public ConfigModule
{
    Q_OBJECT
    
public:
    explicit DifficultyPage(QWidget *parent = 0);
    ~DifficultyPage();
    
    QString pageName();

    void save();
    void load();
    void loadDefaults();

private:
    Ui::DifficultyPage *ui;
    QList<DifficultySubpage*> subpages;

    void setSplitterPos();
    void addPages();
};

#endif // DIFFICULTY_PAGE_H
