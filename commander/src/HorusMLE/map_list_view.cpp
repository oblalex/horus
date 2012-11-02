#include "map_list_view.h"

#include <iostream>
using namespace std;

MapListView::MapListView(QWidget *parent)
    : QGraphicsView(parent)
{
    QRect rect = parent->contentsRect();

    scene = new QGraphicsScene(this);
    scene->setItemIndexMethod(QGraphicsScene::NoIndex);
    scene->setSceneRect(0, 0, rect.width(), rect.height());
    setScene(scene);
    setCacheMode(CacheBackground);
    setViewportUpdateMode(BoundingRectViewportUpdate);
    setRenderHint(QPainter::Antialiasing);
    setTransformationAnchor(AnchorUnderMouse);
    scale(qreal(1), qreal(1));
    setMinimumSize(50, 50);
}

void MapListView::zoomIn()
{
}

void MapListView::zoomOut()
{
}

void MapListView::keyPressEvent(QKeyEvent *event)
{
}

void MapListView::timerEvent(QTimerEvent *event)
{
}

void MapListView::wheelEvent(QWheelEvent *event)
{
}

void MapListView::drawBackground(QPainter *painter, const QRectF &rect)
{
    Q_UNUSED(rect);
    QRectF sceneRect = this->sceneRect();
    painter->setBrush(Qt::NoBrush);
    painter->drawRect(sceneRect);
}

void MapListView::scaleView(qreal scaleFactor)
{
}
