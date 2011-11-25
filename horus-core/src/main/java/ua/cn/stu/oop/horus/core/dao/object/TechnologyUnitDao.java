package ua.cn.stu.oop.horus.core.dao.object;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.series.SeriesCarrierDao;
import ua.cn.stu.oop.horus.core.domain.object.TechnologyUnit;

/**
 *
 * @author alex
 */
public interface TechnologyUnitDao 
        extends  GenericDao<TechnologyUnit>,
        SeriesCarrierDao<TechnologyUnit>{
    
}
