#include "edge.h"

#include <math.h>

static const double Pi = 3.14159265358979323846264338327950288419717;
static double TwoPi = 2.0 * Pi;

#include <iostream>
using namespace std;

Edge::Edge(MissionElem *src, MissionElem *dst, int etype)
    : src(src),
      dst(dst),
      arrowSize(10),
      etype(etype)
{
    setAcceptedMouseButtons(0);
    this->src->addEdge(this);
    this->dst->addEdge(this);
    adjust();
}

void Edge::adjust()
{
    if (!src || !dst)
        return;

    QLineF line(mapFromItem(src, 0, 0), mapFromItem(dst, 0, 0));
    qreal length = line.length();

    prepareGeometryChange();

    if (length > qreal(20.)) {
        QPointF edgeOffset((line.dx() * ME_RADIUS) / length, (line.dy() * ME_RADIUS) / length);
        srcPoint = line.p1() + edgeOffset;
        dstPoint = line.p2() - edgeOffset;
    } else {
        srcPoint = dstPoint = line.p1();
    }
}

QRectF Edge::boundingRect() const
{
    if (!src || !dst)
        return QRectF();

    qreal penWidth = 1;
    qreal extra = (penWidth + arrowSize) / 2.0;

    return QRectF(srcPoint, QSizeF(dstPoint.x() - srcPoint.x(),
                                      dstPoint.y() - srcPoint.y()))
        .normalized()
        .adjusted(-extra, -extra, extra, extra);
}

void Edge::paint(QPainter *painter, const QStyleOptionGraphicsItem *, QWidget *)
{
    if (!src || !dst)
        return;

    QLineF line(srcPoint, dstPoint);
    if (qFuzzyCompare(line.length(), qreal(0.)))
        return;

    QColor lineColor;
    QColor fillColor;

    switch (etype)
    {
        case EDGE_RED:
            lineColor.setRgb(180, 2, 2);
            fillColor.setRgb(180, 40, 40);
            lineColor.light(100);
            fillColor.light(120);
            break;
        case EDGE_BLUE:
            lineColor.setRgb(2, 2, 180);
            fillColor.setRgb(40, 40, 180);
            lineColor.light(100);
            fillColor.light(120);
            break;
        case EDGE_NONE:
        default:
            lineColor.setRgb(2, 2, 2);
            fillColor.setRgb(40, 40, 40);
            break;
    }

    painter->setPen(QPen(lineColor, 2, Qt::SolidLine, Qt::RoundCap, Qt::RoundJoin));

    if (etype == EDGE_NONE)
    {
        painter->drawLine(line);

        double angle = ::acos(line.dx() / line.length());
        if (line.dy() >= 0)
            angle = TwoPi - angle;

        QPointF dstArrowP1 = dstPoint + QPointF(sin(angle - Pi / 3) * arrowSize,
                                                  cos(angle - Pi / 3) * arrowSize);
        QPointF dstArrowP2 = dstPoint + QPointF(sin(angle - Pi + Pi / 3) * arrowSize,
                                                  cos(angle - Pi + Pi / 3) * arrowSize);

        painter->setBrush(fillColor);
        painter->drawPolygon(QPolygonF() << line.p2() << dstArrowP1 << dstArrowP2);
    } else {
        int diff = 100/line.length()*35;
        QLineF diffLine(srcPoint, dstPoint);

        if (etype == EDGE_BLUE)
            diffLine.setAngle(diffLine.angle()+diff);
        else
            diffLine.setAngle(diffLine.angle()-diff);

        qreal rads = diff*(Pi/180);
        qreal newLen = diffLine.length()+(diffLine.length()*(1-cos(rads)));
        diffLine.setLength(newLen);

        QPointF cPoint((diffLine.p1().x()+diffLine.p2().x()) /2,
                       (diffLine.p1().y()+diffLine.p2().y()) /2);

        QLineF dstLine(dst->pos(), dstPoint);
        if (etype == EDGE_BLUE)
            dstLine.setAngle(dstLine.angle()-20);
        else
            dstLine.setAngle(dstLine.angle()+20);

        QPainterPath ePath;
        ePath.moveTo(srcPoint);
        ePath.quadTo(cPoint, dstLine.p2());
        painter->drawPath(ePath);

        double angle = ::acos(dstLine.dx() / dstLine.length());
        if (dstLine.dy() >= 0)
            angle = TwoPi - angle;

        QPointF dstArrowP1 = dstLine.p2() - QPointF(sin(angle - Pi / 3) * arrowSize,
                                                 cos(angle - Pi / 3) * arrowSize);
        QPointF dstArrowP2 = dstLine.p2() - QPointF(sin(angle - Pi + Pi / 3) * arrowSize,
                                                  cos(angle - Pi + Pi / 3) * arrowSize);

        painter->setBrush(fillColor);
        painter->drawPolygon(QPolygonF() << dstLine.p2() << dstArrowP1 << dstArrowP2);
    }
}
