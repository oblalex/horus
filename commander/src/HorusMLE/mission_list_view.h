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

signals:
    void missionSelected();
    void missionDeselected();

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

    void setHighlighted(MissionElem* me);
    void setUnhighlighted(MissionElem* me);
    MissionElem* getHighlighted();

    MissionElem* getCurrent();
    void checkCurrent(MissionElem* me);

    void setChanged(bool value);
    bool isChanged();

public slots:
    void zoomIn();
    void zoomOut();

protected:
    void mousePressEvent(QMouseEvent *event);
    void wheelEvent(QWheelEvent *event);
    void drawBackground(QPainter *painter, const QRectF &rect);
    void scaleView(qreal factor);

private:

    QList<MissionElem*> missions;
    MissionElem* active;
    MissionElem* current;
    MissionElem* highlighted;

    QPointF currentCenterPoint;
    QPoint lastPanPoint;

    qreal scaleFactor;

    bool changed;
};

#endif // MAP_LIST_VIEW_H
