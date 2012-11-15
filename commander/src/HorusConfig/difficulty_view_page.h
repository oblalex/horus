#ifndef DIFFICULTY_VIEW_PAGE_H
#define DIFFICULTY_VIEW_PAGE_H

#include <QWidget>
#include "difficulty_subpage.h"

namespace Ui {
class DifficultyViewPage;
}

class DifficultyViewPage : public QWidget, public DifficultySubpage
{
    Q_OBJECT
    
public:
    explicit DifficultyViewPage(QWidget *parent = 0);
    ~DifficultyViewPage();

    quint64 getDifficultyCode();
    void setDifficultyCode(quint64 value);

private slots:
    void on_NoOutSideViews_toggled(bool checked);

private:
    Ui::DifficultyViewPage *ui;
};

#endif // DIFFICULTY_VIEW_PAGE_H
