#ifndef EDGE_H
#define EDGE_H

#include <QGraphicsItem>
#include "mission_elem.h"

class MissionElem;

enum EDGE_TYPE {EDGE_NONE, EDGE_RED, EDGE_BLUE};

class Edge : public QGraphicsItem
{
public:

    Edge(MissionElem* src, MissionElem* dst, int etype);
    ~Edge();

    void adjust();

    enum { Type = UserType + 2 };
    int type() const { return Type; }

    MissionElem* getDst();
    MissionElem* getSrc();

protected:
    QRectF boundingRect() const;
    void paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget);

private:
    MissionElem *src, *dst;

    QPointF srcPoint;
    QPointF dstPoint;
    qreal arrowSize;
    int etype;
};

#endif // EDGE_H
