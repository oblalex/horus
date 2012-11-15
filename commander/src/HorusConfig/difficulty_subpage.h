#ifndef DIFFICULTY_SUBPAGE_H
#define DIFFICULTY_SUBPAGE_H

#include <QObject>

class DifficultySubpage
{
public:
    DifficultySubpage();
    virtual quint64 getDifficultyCode() = 0;
    virtual void setDifficultyCode(quint64 value) = 0;
};

#endif // DIFFICULTY_SUBPAGE_H
