#ifndef MISSION_ELEM_H
#define MISSION_ELEM_H

#include <QGraphicsItem>
#include <QList>

#include "map_list_view.h"
#include "edge.h"
#include "d_mission.h"

class Edge;
class MissionElem;

class MissionElem: public QGraphicsItem
{
public:
    MissionElem(MapListView* MLV);

    D_MISSION_LITE data;

    bool isCurrent;

    MissionElem* nextNone;
    MissionElem* nextRed;
    MissionElem* nextBlue;

    QList<Edge*> edges;
    int refsCount;
    MapListView* MLV;

    enum { Type = UserType + 1 };
    int type() const { return Type; }

    QRectF boundingRect() const;
    QPainterPath shape() const;
    void paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget);

protected:
    QVariant itemChange(GraphicsItemChange change, const QVariant &value);

    void mousePressEvent(QGraphicsSceneMouseEvent *event);
    void mouseReleaseEvent(QGraphicsSceneMouseEvent *event);
};

#endif // MISSION_ELEM_H
