#include "footest.h"
#include <QTest>

FooTest::FooTest(QObject *parent) :
    QObject(parent)
{
}

void FooTest::fooMethod()
{
    int i = 5;
    int j = i++;

    QCOMPARE(j, 5);
}
