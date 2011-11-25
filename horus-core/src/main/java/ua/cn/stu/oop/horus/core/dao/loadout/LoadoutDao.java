package ua.cn.stu.oop.horus.core.dao.loadout;

import ua.cn.stu.oop.horus.core.dao.*;
import ua.cn.stu.oop.horus.core.domain.loadout.Loadout;

/**
 *
 * @author alex
 */
public interface LoadoutDao 
        extends GenericDao<Loadout>,
        ParentCarrierDao<Loadout>{
    
}
