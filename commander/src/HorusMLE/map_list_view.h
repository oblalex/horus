#ifndef MAP_LIST_VIEW_H
#define MAP_LIST_VIEW_H

#include <QtGui/QGraphicsView>

class MapListView : public QGraphicsView
{
    Q_OBJECT

public:
    MapListView(QWidget *parent = 0);

public slots:
    void zoomIn();
    void zoomOut();

protected:
    void keyPressEvent(QKeyEvent *event);
    void timerEvent(QTimerEvent *event);
    void wheelEvent(QWheelEvent *event);
    void drawBackground(QPainter *painter, const QRectF &rect);

    void scaleView(qreal scaleFactor);

private:
    int timerId;
};

#endif // MAP_LIST_VIEW_H
