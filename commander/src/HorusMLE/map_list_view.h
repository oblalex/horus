#ifndef MAP_LIST_VIEW_H
#define MAP_LIST_VIEW_H

#include <QtGui/QGraphicsView>
#include <QList>
#include "mission_elem.h"

class MissionElem;

class MapListView : public QGraphicsView
{
    Q_OBJECT

public:
    MapListView(QWidget *parent = 0);

    MissionElem* missionByName(QString name);
    void addMission(MissionElem* me);
    QList<MissionElem*> getMissions();
    int missionsCount();
    void missionsClear();

    QGraphicsScene *scene;

    void setActive(MissionElem* me);
    MissionElem* getActive();
    void unsetActive();

public slots:
    void zoomIn();
    void zoomOut();

protected:
    void keyPressEvent(QKeyEvent *event);
    void wheelEvent(QWheelEvent *event);
    void drawBackground(QPainter *painter, const QRectF &rect);

    void scaleView(qreal scaleFactor);

private:
    QList<MissionElem*> missions;
    MissionElem* active;
};

#endif // MAP_LIST_VIEW_H
