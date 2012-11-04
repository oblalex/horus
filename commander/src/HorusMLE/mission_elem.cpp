#include "mission_elem.h"

#include <QGraphicsScene>
#include <QGraphicsSceneMouseEvent>
#include <QPainter>
#include <QStyleOption>

#include <iostream>
using namespace std;

MissionElem::MissionElem(MapListView* MLV)
    : MLV(MLV)
{
    setFlag(ItemIsMovable);
    setFlag(ItemSendsGeometryChanges);
    setCacheMode(DeviceCoordinateCache);
    setZValue(-1);
}

void MissionElem::addEdge(Edge *edge)
{
    edgeList << edge;
    edge->adjust();
}

QList<Edge *> MissionElem::edges() const
{
    return edgeList;
}

QRectF MissionElem::boundingRect() const
{
    qreal adjust = 5;
    return QRectF(-ME_RADIUS - adjust, -ME_RADIUS - adjust,
                  ME_RADIUS*2 + adjust, ME_RADIUS*2 + adjust);
}

QPainterPath MissionElem::shape() const
{
    QPainterPath path;
    path.addEllipse(-ME_RADIUS, -ME_RADIUS, ME_RADIUS*2, ME_RADIUS*2);
    return path;
}

void MissionElem::paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget)
{
    QRadialGradient gradient(-ME_RADIUS/5, -ME_RADIUS/5, ME_RADIUS-(ME_RADIUS/10));

    if (isCurrent)
    {
        gradient.setColorAt(0, QColor(98, 246, 55).light(120));
        gradient.setColorAt(1, QColor(70, 137, 35).light(120));
    } else {
        gradient.setColorAt(0, QColor(212, 235, 34).light(120));
        gradient.setColorAt(1, QColor(173, 110, 64).light(120));
    }

    painter->setBrush(gradient);
    painter->setPen(QPen(QColor(45, 45, 45), 2));
    painter->drawEllipse(-ME_RADIUS, -ME_RADIUS, ME_RADIUS*2, ME_RADIUS*2);
}

QVariant MissionElem::itemChange(QGraphicsItem::GraphicsItemChange change, const QVariant &value)
{
    switch (change)
    {
        case ItemPositionHasChanged:
            // TODO:
            break;
        default:
            break;
    };

    return QGraphicsItem::itemChange(change, value);
}

void MissionElem::mousePressEvent(QGraphicsSceneMouseEvent *event)
{
}

void MissionElem::mouseReleaseEvent(QGraphicsSceneMouseEvent *event)
{
}
