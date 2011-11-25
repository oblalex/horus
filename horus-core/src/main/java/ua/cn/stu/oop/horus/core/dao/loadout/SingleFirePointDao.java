package ua.cn.stu.oop.horus.core.dao.loadout;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.weapon.WeaponCarrierDao;
import ua.cn.stu.oop.horus.core.domain.loadout.SingleFirePoint;

/**
 *
 * @author alex
 */
public interface SingleFirePointDao  
        extends GenericDao<SingleFirePoint>,
        WeaponCarrierDao<SingleFirePoint>{
    
}
