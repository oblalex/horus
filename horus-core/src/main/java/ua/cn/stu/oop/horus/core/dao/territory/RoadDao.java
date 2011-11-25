package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.Road;

/**
 *
 * @author alex
 */
public interface RoadDao
        extends GenericDao<Road>,
        NodeCarrierDao<Road>,
        TypeCarrierDao<Road> {
    
}
