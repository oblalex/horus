#include "mission_list_view.h"
#include <typeinfo>

#include <iostream>
using namespace std;

MissionListView::MissionListView(QWidget *parent)
    : QGraphicsView(parent),
      active(NULL),
      current(NULL),
      highlighted(NULL),
      scaleFactor(1)
{
    scene = new QGraphicsScene(this);
    scene->setItemIndexMethod(QGraphicsScene::NoIndex);
    scene->setSceneRect(scene->itemsBoundingRect());
    setScene(scene);
    setCacheMode(CacheBackground);
    setViewportUpdateMode(BoundingRectViewportUpdate);
    setRenderHint(QPainter::Antialiasing);
    setTransformationAnchor(AnchorUnderMouse);
    scale(scaleFactor, scaleFactor);
    setMinimumSize(100, 100);
}

MissionElem *MissionListView::missionByName(QString name)
{
    foreach (MissionElem* me, missions)
    {
            if (me->data.name == name)
            return me;
    }
    return NULL;
}

void MissionListView::addMission(MissionElem *me)
{
    missions << me;
    me->updateToolTip();
    scene->addItem(me);
    checkCurrent(me);
}

void MissionListView::rmMission(MissionElem *me)
{
    if (me==current) current = NULL;
    active = NULL;

    missions.removeOne(me);
    scene->removeItem(me);
    delete me;
    scene->update();
}

QList<MissionElem *> MissionListView::getMissions()
{
    return QList<MissionElem *>(missions);
}

int MissionListView::missionsCount()
{
    return missions.count();
}

void MissionListView::missionsClear()
{
    missions.clear();
    setActive(NULL);
    current = NULL;
    highlighted = NULL;
}

void MissionListView::setActive(MissionElem *me)
{
    MissionElem *old = active;
    active = me;

    if ((old!=NULL) && (old != me))
        old->update();
}

MissionElem *MissionListView::getActive()
{
    return active;
}

void MissionListView::setHighlighted(MissionElem *me)
{
    highlighted = me;
}

void MissionListView::setUnhighlighted(MissionElem *me)
{
    if (highlighted==me)
        highlighted = NULL;
}

MissionElem *MissionListView::getHighlighted()
{
    return highlighted;
}

MissionElem *MissionListView::getCurrent()
{
    return current;
}

void MissionListView::checkCurrent(MissionElem *me)
{
    if (me->isCurrent) {
        if ((current!=NULL) && (me!=current))
        {
            current->isCurrent = false;
            current->update();
        }
        current = me;
    } else {
        if (me==current)
            current = NULL;
    }
}

void MissionListView::zoomIn()
{
    scaleView(1.1);
}

void MissionListView::zoomOut()
{
    scaleView(1/1.1);
}

void MissionListView::mousePressEvent(QMouseEvent *event)
{
    Q_UNUSED(event)

    if (highlighted==NULL)
        setActive(NULL);
    QGraphicsView::mousePressEvent(event);
}

void MissionListView::scaleView(qreal factor)
{
    scale(factor, factor);
}

void MissionListView::wheelEvent(QWheelEvent *event)
{
    if(event->delta() > 0)
    {
        zoomIn();
    } else {
        zoomOut();
    }
}

void MissionListView::drawBackground(QPainter *painter, const QRectF &rect)
{
    Q_UNUSED(rect);
    Q_UNUSED(painter);
}
