package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.TheatreOfWar;

/**
 *
 * @author alex
 */
public interface TheatreOfWarDao
        extends GenericDao<TheatreOfWar>,
        TitleLinkCarrierDao<TheatreOfWar>,
        NodeCarrierDao<TheatreOfWar> {
    
}
