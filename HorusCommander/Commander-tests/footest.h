#ifndef FOOTEST_H
#define FOOTEST_H

#include <QObject>

class FooTest : public QObject
{
    Q_OBJECT

public:
    explicit FooTest(QObject *parent = 0);

private slots:
    void fooMethod();
};

#endif // FOOTEST_H
