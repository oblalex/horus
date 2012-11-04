#include "list_file_helper.h"

#include <QFile>
#include <QDomDocument>
#include <QDomElement>
#include <QTextStream>
#include <QList>
#include <typeinfo>
#include "edge.h"

#include <iostream>
using namespace std;

#define XML_ROOT            ("missions")
#define XML_ELEM            ("mission")

#define XML_ATTR_NAME       ("name")
#define XML_ATTR_PATH       ("path")
#define XML_ATTR_DURATION   ("duration")
#define XML_ATTR_NEXT       ("next")
#define XML_ATTR_NEXT_RED   ("nextRed")
#define XML_ATTR_NEXT_BLUE  ("nextBlue")
#define XML_ATTR_POSX       ("x")
#define XML_ATTR_POSY       ("y")
#define XML_ATTR_IS_CURRENT ("isCurrent")

#define IS_CURRENT_TRUE_VAL ("1")
#define IS_CURRENT_FALSE_VAL ("0")

#define DEFAULT_MISSION_DURATION ("3600")

static QString listPath = "missions.xml";
static QDomDocument doc("missions");

ListFileHelper::ListFileHelper(MapListView *view)
    : view(view)
{
}

void ListFileHelper::loadToView()
{
    //if (loaded) unload;
    loaded = false;

    QFile file(listPath);

    if(file.open(QIODevice::ReadOnly) == false)
        return;

    if(doc.setContent(&file) == false)
    {
        file.close();
        return;
    }

    file.close();

    QDomElement root = doc.documentElement();

    if(root.tagName() != XML_ROOT)
        return;



    QDomNode n;
    n = root.firstChild();
    while(n.isNull()==false)
    {
        QDomElement e = n.toElement();
        if(e.isNull()==false)
        {
            if(e.tagName() == XML_ELEM)
                addFromElement(&e);
        }
        n = n.nextSibling();
    }

    n = root.firstChild();
    resolveReferences(n);

    loaded = true;
}

void ListFileHelper::saveFromView()
{
    saved = false;
}

bool ListFileHelper::isLoaded()
{
    return loaded;
}

bool ListFileHelper::isSaved()
{
    return saved;
}

void ListFileHelper::addFromElement(QDomElement *e)
{
    QString name = e->attribute(XML_ATTR_NAME, "");
    if (name.isEmpty()) return;

    QString path = e->attribute(XML_ATTR_PATH, "");
    if (path.isEmpty()) return;

    MissionElem* me = new MissionElem(view);

    me->data.name = (char*) malloc(name.size()+1);
    memset(me->data.name, 0, name.size()+1);
    memcpy(me->data.name, name.toStdString().c_str(), name.size());

    me->data.path = (char*) malloc(path.size());
    memcpy(me->data.path, path.toStdString().c_str(), path.size());

    me->isCurrent = e->attribute(XML_ATTR_IS_CURRENT, IS_CURRENT_FALSE_VAL) == IS_CURRENT_TRUE_VAL;
    me->data.sDuration = e->attribute(XML_ATTR_DURATION, DEFAULT_MISSION_DURATION).toInt();

    QString pos;
    pos = e->attribute(XML_ATTR_POSX, "0");
    int x = pos.toInt();

    pos = e->attribute(XML_ATTR_POSY, "0");
    int y = pos.toInt();

    view->scene->addItem(me);
    me->setPos(x, y);
}

void ListFileHelper::resolveReferences(QDomNode& first)
{
    QList<QGraphicsItem *> items = view->scene->items();
    MissionElem* me;
    QString name;
    QString attr;
    bool found;
    QDomNode n;
    QDomElement e;

    foreach (QGraphicsItem* item, items)
    {
        try
        {
            me = qgraphicsitem_cast<MissionElem *>(item);
        } catch (bad_cast& bc) {
            Q_UNUSED(bc)
            continue;
        }

        name = QString(me->data.name);

        found = false;
        n = first;
        while(n.isNull()==false)
        {
            e = n.toElement();
            if(e.isNull()==false)
            {
                attr = e.attribute(XML_ATTR_NAME, "");
                if (attr==name)
                {
                    found = true;
                    break;
                }
            }
            n = n.nextSibling();
        }
        if (found == false)
            continue;

        QString nameNone = e.attribute(XML_ATTR_NEXT, "");
        QString nameRed  = e.attribute(XML_ATTR_NEXT_RED, "");
        QString nameBlue = e.attribute(XML_ATTR_NEXT_BLUE, "");

        me->nextNone = (nameNone.isEmpty())?NULL:view->missionByName(nameNone);
        me->nextRed = (nameRed==nameNone)
                ?me->nextNone
               :(nameRed.isEmpty())
                 ?NULL
                :view->missionByName(nameRed);
        me->nextBlue = (nameBlue==nameNone)
                ?me->nextNone
               :(nameBlue==nameRed)
                 ?me->nextRed
                :(nameBlue.isEmpty())
                  ?NULL
                 :view->missionByName(nameBlue);

        if (me->nextNone)
            view->scene->addItem(new Edge(me, me->nextNone, EDGE_NONE));
        if (me->nextRed)
            view->scene->addItem(new Edge(me, me->nextRed, EDGE_RED));
        if (me->nextBlue)
            view->scene->addItem(new Edge(me, me->nextBlue, EDGE_BLUE));
    }
}
