#include "list_file_helper.h"

#include <QFile>
#include <QDomDocument>
#include <QDomElement>
#include <QTextStream>
#include <QList>

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
    resolveReferences(&n);

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

    me->data.name = (char*) malloc(name.size());
    memcpy(me->data.name, name.constData(), name.size());

    me->data.path = (char*) malloc(path.size());
    memcpy(me->data.path, path.constData(), path.size());

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
    foreach (QGraphicsItem* item, items)
    {
        me = qgraphicsitem_cast<MissionElem *>(item);
        name = me->data.name;

        bool found = false;
        QDomNode n;
        for(n = first; n != NULL; n = n.nextSibling())
        {
            QDomElement e = n.toElement();
            if (e==NULL)
                continue;
            attr = e.attribute(XML_ATTR_NAME, "");
            if (attr==name)
            {
                found = true;
                break;
            }
        }
        if (found)
        {
            // TODO:
        }
    }
}
