#ifndef DIFFICULTY_MAP_ICONS_PAGE_H
#define DIFFICULTY_MAP_ICONS_PAGE_H

#include <QWidget>
#include "difficulty_subpage.h"

namespace Ui {
class DifficultyMapIconsPage;
}

class DifficultyMapIconsPage : public QWidget, public DifficultySubpage
{
    Q_OBJECT
    
public:
    explicit DifficultyMapIconsPage(QWidget *parent = 0);
    ~DifficultyMapIconsPage();
    
    quint64 getDifficultyCode();
    void setDifficultyCode(quint64 value);

private slots:
    void on_NoMapIcons_toggled(bool checked);

private:
    Ui::DifficultyMapIconsPage *ui;
};

#endif // DIFFICULTY_MAP_ICONS_PAGE_H
