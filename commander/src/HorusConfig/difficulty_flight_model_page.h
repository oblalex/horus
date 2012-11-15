#ifndef DIFFICULTY_FLIGHT_MODEL_PAGE_H
#define DIFFICULTY_FLIGHT_MODEL_PAGE_H

#include <QWidget>
#include "difficulty_subpage.h"

namespace Ui {
class DifficultyFlightModelPage;
}

class DifficultyFlightModelPage : public QWidget, public DifficultySubpage
{
    Q_OBJECT
    
public:
    explicit DifficultyFlightModelPage(QWidget *parent = 0);
    ~DifficultyFlightModelPage();

    quint64 getDifficultyCode();
    void setDifficultyCode(quint64 value);
    
private:
    Ui::DifficultyFlightModelPage *ui;
};

#endif // DIFFICULTY_FLIGHT_MODEL_PAGE_H
