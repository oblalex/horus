package ua.cn.stu.oop.horus.core.dao.object;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.object.Building;

/**
 *
 * @author alex
 */
public interface BuildingDao 
        extends GenericDao<Building>,
        TypeCarrierDao<Building>{
    
}
