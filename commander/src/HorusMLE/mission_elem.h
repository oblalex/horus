#ifndef MISSION_ELEM_H
#define MISSION_ELEM_H

#include <QGraphicsItem>
#include <QList>

#include "mission_list_view.h"
#include "edge.h"
#include "d_mission.h"

#define DEFAULT_RADIUS (25)
#define DEFAULT_MISSION_DURATION ("3600")

class Edge;
class MissionElem;
class MissionListView;

class MissionElem: public QGraphicsItem
{
public:
    MissionElem(MissionListView* MLV);
    ~MissionElem();

    D_MISSION_LITE data;

    bool isCurrent;

    MissionElem* nextNone;
    MissionElem* nextRed;
    MissionElem* nextBlue;

    void addEdge(Edge *edge);
    void rmEdge(Edge *edge);
    void rmDstEdges();
    void updateDstEdges();
    void rmEdges();
    QList<Edge *> edges() const;
    int refsCount;

    MissionListView* MLV;

    enum { Type = UserType + 1 };
    int type() const { return Type; }

    QRectF boundingRect() const;
    QPainterPath shape() const;
    void paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget);

    int getRadius();
    void updateToolTip();
    void clearDynamicStrings();

protected:
    QVariant itemChange(GraphicsItemChange change, const QVariant &value);

    void mousePressEvent(QGraphicsSceneMouseEvent *event);
    void mouseReleaseEvent(QGraphicsSceneMouseEvent *event);

private:
    void updateRadius();
    QRect getTextRect(QString text) const;
    QList<Edge*> edgeList;
    int radius;
};

#endif // MISSION_ELEM_H
