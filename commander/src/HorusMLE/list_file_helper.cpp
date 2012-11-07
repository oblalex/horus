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

static QString listPath = "missions.xml";
static QDomDocument doc("missions");

ListFileHelper::ListFileHelper(MissionListView *view)
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

    QFile file(listPath);

    if(file.open(QIODevice::WriteOnly) == false)
        return;

    doc.clear();

    QDomProcessingInstruction pi = doc.createProcessingInstruction("xml", "version=\"1.0\" encoding=\"UTF-8\"");
    doc.insertBefore(pi, QDomNode());

    QDomElement root = doc.createElement(XML_ROOT);
    doc.appendChild(root);

    foreach (MissionElem* me, view->getMissions())
    {
        QDomElement xml_elem = doc.createElement(XML_ELEM);

        xml_elem.setAttribute(XML_ATTR_NAME, me->data.name);
        xml_elem.setAttribute(XML_ATTR_PATH, me->data.path);
        xml_elem.setAttribute(XML_ATTR_DURATION, QString::number(me->data.sDuration));
        xml_elem.setAttribute(XML_ATTR_POSX, QString::number(me->pos().x()));
        xml_elem.setAttribute(XML_ATTR_POSY, QString::number(me->pos().y()));

        if (me->nextNone)
            xml_elem.setAttribute(XML_ATTR_NEXT, me->nextNone->data.name);
        if (me->nextRed)
            xml_elem.setAttribute(XML_ATTR_NEXT_RED, me->nextRed->data.name);
        if (me->nextBlue)
            xml_elem.setAttribute(XML_ATTR_NEXT_BLUE, me->nextBlue->data.name);

        if (me->isCurrent)
            xml_elem.setAttribute(XML_ATTR_IS_CURRENT, IS_CURRENT_TRUE_VAL);

        root.appendChild(xml_elem);
    }

    QTextStream ts(&file);
    ts << doc.toString();
    file.close();

    saved = true;
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

    me->setName(&name);
    me->setPath(&path);

    me->isCurrent = e->attribute(XML_ATTR_IS_CURRENT, IS_CURRENT_FALSE_VAL) == IS_CURRENT_TRUE_VAL;
    me->data.sDuration = e->attribute(XML_ATTR_DURATION, DEFAULT_MISSION_DURATION).toInt();

    QString pos;
    pos = e->attribute(XML_ATTR_POSX, "0");
    int x = pos.toInt();

    pos = e->attribute(XML_ATTR_POSY, "0");
    int y = pos.toInt();

    view->addMission(me);
    me->setPos(x, y);
}

void ListFileHelper::resolveReferences(QDomNode& first)
{
    QString name;
    QString attr;
    bool found;
    QDomNode n;
    QDomElement e;

    foreach (MissionElem* me, view->getMissions())
    {
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

        me->updateDstEdges();
    }
}
