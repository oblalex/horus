#ifndef DIFFICULTY_VAR_PAGE_H
#define DIFFICULTY_VAR_PAGE_H

#include <QWidget>
#include "difficulty_subpage.h"

namespace Ui {
class DifficultyVarPage;
}

class DifficultyVarPage : public QWidget, public DifficultySubpage
{
    Q_OBJECT
    
public:
    explicit DifficultyVarPage(QWidget *parent = 0);
    ~DifficultyVarPage();
    
    quint64 getDifficultyCode();
    void setDifficultyCode(quint64 value);

private:
    Ui::DifficultyVarPage *ui;
};

#endif // DIFFICULTY_VAR_PAGE_H
