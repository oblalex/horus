#include "mission_elem.h"

#include <QGraphicsScene>
#include <QGraphicsSceneMouseEvent>
#include <QPainter>
#include <QStyleOption>

#include <iostream>
using namespace std;

MissionElem::MissionElem(MapListView* MLV)
    : refsCount(0),
      MLV(MLV),
      radius(DEFAULT_RADIUS)
{
    setFlag(ItemIsMovable);
    setFlag(ItemSendsGeometryChanges);
    setCacheMode(DeviceCoordinateCache);
    setZValue(-1);
}

void MissionElem::addEdge(Edge *edge)
{
    edgeList << edge;
    updateRadius();

    if (edge->getDst()==this)
        refsCount++;

    foreach (Edge *e, edgeList)
        e->adjust();
}

void MissionElem::rmEdge(Edge *edge)
{
    edgeList.removeOne(edge);
    updateRadius();

    foreach (Edge *e, edgeList)
        e->adjust();
}

void MissionElem::updateRadius()
{
    radius = DEFAULT_RADIUS+(edges().count()*1.5f);
}

QList<Edge *> MissionElem::edges() const
{
    return edgeList;
}

QRectF MissionElem::boundingRect() const
{
    qreal adjust = 5;
    QString text = QString(data.name);
    QRect textRec = getTextRect(text);

    return QRectF(-radius - adjust, -radius - adjust,
                  radius*2 + adjust, radius*2 + adjust).united(textRec);
}

QPainterPath MissionElem::shape() const
{
    QPainterPath path;
    path.addEllipse(-radius, -radius, radius*2, radius*2);
    return path;
}

void MissionElem::paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget)
{
    Q_UNUSED(option)
    Q_UNUSED(widget)

    QRadialGradient gradient(-radius/5, -radius/5, radius-(radius/10));

    if (isCurrent)
    {
        gradient.setColorAt(0, QColor(98, 246, 55).light(120));
        gradient.setColorAt(1, QColor(70, 137, 35).light(120));
    } else if (refsCount==0)
    {
        gradient.setColorAt(0, QColor(240, 80, 80).light(120));
        gradient.setColorAt(1, QColor(173, 30, 30).light(120));
    } else {
        gradient.setColorAt(0, QColor(212, 235, 34).light(120));
        gradient.setColorAt(1, QColor(173, 110, 64).light(120));
    }

    painter->setBrush(gradient);
    painter->setPen(QPen(QColor(45, 45, 45), 2));
    painter->drawEllipse(-radius, -radius, radius*2, radius*2);

    QString text = QString(data.name);
    QRect textRect = getTextRect(text);

    painter->setBrush(Qt::white);
    painter->setPen(Qt::NoPen);
    painter->drawRect(textRect);
    painter->setPen(Qt::black);
    painter->drawText(QRect(textRect.left(), textRect.top(), textRect.width(), textRect.height()),
                      Qt::AlignCenter, text);
}

int MissionElem::getRadius()
{
    return radius;
}

void MissionElem::updateToolTip()
{
    QString toolTip;
    toolTip.append(QObject::tr("Name: ")).append(data.name).append(".\n");
    toolTip.append(QObject::tr("Path: ")).append(data.path).append(".\n");
    toolTip.append(QObject::tr("Duration: ")).append(QString::number(data.sDuration)).append(" "+QObject::tr("s")+".");
    setToolTip(toolTip);
}

void MissionElem::rmDstEdges()
{
    foreach (Edge* e, edgeList)
    {
        if (this==e->getDst()) continue;
        MLV->scene->removeItem(e);
        delete e;
    }
}

void MissionElem::rmEdges()
{
    foreach (Edge* e, edgeList)
    {
        MLV->scene->removeItem(e);
        delete e;
    }
}

QVariant MissionElem::itemChange(QGraphicsItem::GraphicsItemChange change, const QVariant &value)
{
    switch (change)
    {
        case ItemPositionHasChanged:
            foreach (Edge *edge, edgeList)
                edge->adjust();
            update();
            MLV->scene->update();
            break;
        default:
            break;
    };

    return QGraphicsItem::itemChange(change, value);
}

void MissionElem::mousePressEvent(QGraphicsSceneMouseEvent *event)
{
    MLV->setActive(this);
    update();
    QGraphicsItem::mousePressEvent(event);
}

void MissionElem::mouseReleaseEvent(QGraphicsSceneMouseEvent *event)
{
    update();
    QGraphicsItem::mouseReleaseEvent(event);
}

QRect MissionElem::getTextRect(QString text) const
{
    QFont font;
    font.setFamily(font.defaultFamily());
    font.setPointSize(8);
    QFontMetrics fm(font);
    int w = fm.width(text)+15;
    return QRect(-w/2, radius+3, w, fm.height()+2);
}
