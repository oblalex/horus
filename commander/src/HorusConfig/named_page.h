#ifndef NAMED_PAGE_H
#define NAMED_PAGE_H

#include <QString>

class NamedPage
{
public:
    NamedPage();

    virtual QString pageName() = 0;
};

#endif // NAMED_PAGE_H
