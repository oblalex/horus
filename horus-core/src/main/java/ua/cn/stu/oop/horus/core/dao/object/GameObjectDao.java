package ua.cn.stu.oop.horus.core.dao.object;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.object.GameObject;

/**
 *
 * @author alex
 */
public interface GameObjectDao
        extends GenericDao<GameObject>,
        TitleLinkCarrierDao<GameObject> {
    
}
