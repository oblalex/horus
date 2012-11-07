#ifndef LIST_FILE_HELPER_H
#define LIST_FILE_HELPER_H

#include "mission_list_view.h"
#include <QDomNode>
#include "mission_elem.h"

class ListFileHelper
{
public:
    ListFileHelper(MissionListView* view);

    void loadToView();
    void saveFromView();

    bool isLoaded();
    bool isSaved();

private:
    void addFromElement(QDomElement *e);
    void resolveReferences(QDomNode &first);

    MissionListView* view;

    bool loaded;
    bool saved;
};

#endif // LIST_FILE_HELPER_H
