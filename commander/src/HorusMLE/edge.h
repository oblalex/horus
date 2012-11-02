#ifndef EDGE_H
#define EDGE_H

#include <QGraphicsItem>
#include "mission_elem.h"

class MissionElem;

class Edge : public QGraphicsItem
{
public:
    Edge(MissionElem* src, MissionElem* dst);
};

#endif // EDGE_H
