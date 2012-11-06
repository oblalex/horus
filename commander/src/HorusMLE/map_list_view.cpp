#include "map_list_view.h"
#include <typeinfo>

#include <iostream>
using namespace std;

MapListView::MapListView(QWidget *parent)
    : QGraphicsView(parent)
{
    scene = new QGraphicsScene(this);
    scene->setItemIndexMethod(QGraphicsScene::NoIndex);
    scene->setSceneRect(scene->itemsBoundingRect());
    setScene(scene);
    setCacheMode(CacheBackground);
    setViewportUpdateMode(BoundingRectViewportUpdate);
    setRenderHint(QPainter::Antialiasing);
    setTransformationAnchor(AnchorUnderMouse);
    scale(qreal(1), qreal(1));
    setMinimumSize(50, 50);
}

MissionElem *MapListView::missionByName(QString name)
{
    foreach (MissionElem* me, missions)
    {
            if (me->data.name == name)
            return me;
    }
    return NULL;
}

void MapListView::addMission(MissionElem *me)
{
    missions << me;
    me->updateToolTip();
    scene->addItem(me);
}

QList<MissionElem *> MapListView::getMissions()
{
    return QList<MissionElem *>(missions);
}

int MapListView::missionsCount()
{
    return missions.count();
}

void MapListView::missionsClear()
{
    missions.clear();
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

void MapListView::wheelEvent(QWheelEvent *event)
{
}

void MapListView::drawBackground(QPainter *painter, const QRectF &rect)
{
    Q_UNUSED(rect);
    Q_UNUSED(painter);
}

void MapListView::scaleView(qreal scaleFactor)
{
}
