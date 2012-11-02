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

QRectF MissionElem::boundingRect() const
{
    qreal adjust = 5;
    return QRectF( -20 - adjust, -20 - adjust,
                  45 + adjust, 45 + adjust);
}

QPainterPath MissionElem::shape() const
{
    QPainterPath path;
    path.addEllipse(-20, -20, 40, 40);
    return path;
}

void MissionElem::paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget)
{
    QRadialGradient gradient(-5, -5, 23);

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
    painter->drawEllipse(-20, -20, 40, 40);
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
