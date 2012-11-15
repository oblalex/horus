#ifndef DIFFICULTY_WEAPONS_PAGE_H
#define DIFFICULTY_WEAPONS_PAGE_H

#include <QWidget>
#include "difficulty_subpage.h"

namespace Ui {
class DifficultyWeaponsPage;
}

class DifficultyWeaponsPage : public QWidget, public DifficultySubpage
{
    Q_OBJECT
    
public:
    explicit DifficultyWeaponsPage(QWidget *parent = 0);
    ~DifficultyWeaponsPage();

    int getDifficultyCode();
    void setDifficultyCode(int value);
    
private:
    Ui::DifficultyWeaponsPage *ui;
};

#endif // DIFFICULTY_WEAPONS_PAGE_H
