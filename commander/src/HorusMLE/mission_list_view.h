#ifndef MAP_LIST_VIEW_H
#define MAP_LIST_VIEW_H

#include <QtGui/QGraphicsView>
#include <QWheelEvent>
#include <QList>
#include "mission_elem.h"

class MissionElem;

class MissionListView : public QGraphicsView
{
    Q_OBJECT

public:
    MissionListView(QWidget *parent = 0);

    MissionElem* missionByName(QString name);
    void addMission(MissionElem* me);
    void rmMission(MissionElem* me);
    QList<MissionElem*> getMissions();
    int missionsCount();
    void missionsClear();

    QGraphicsScene *scene;

    void setActive(MissionElem* me);
    MissionElem* getActive();
    void unsetActive();

    MissionElem* getCurrent();
    void checkCurrent(MissionElem* me);

public slots:
    void zoomIn();
    void zoomOut();

protected:
    void wheelEvent(QWheelEvent *event);
    void drawBackground(QPainter *painter, const QRectF &rect);
    void scaleView(qreal factor);

private:

    QList<MissionElem*> missions;
    MissionElem* active;
    MissionElem* current;

    QPointF currentCenterPoint;
    QPoint lastPanPoint;

    qreal scaleFactor;
};

#endif // MAP_LIST_VIEW_H
