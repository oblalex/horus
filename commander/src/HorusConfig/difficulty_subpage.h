#ifndef DIFFICULTY_SUBPAGE_H
#define DIFFICULTY_SUBPAGE_H

class DifficultySubpage
{
public:
    DifficultySubpage();
    virtual int getDifficultyCode() = 0;
    virtual void setDifficultyCode(int value) = 0;
};

#endif // DIFFICULTY_SUBPAGE_H
