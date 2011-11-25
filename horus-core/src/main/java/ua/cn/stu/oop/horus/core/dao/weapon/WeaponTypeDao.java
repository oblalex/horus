package ua.cn.stu.oop.horus.core.dao.weapon;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.weapon.WeaponType;

/**
 *
 * @author alex
 */
public interface WeaponTypeDao  
        extends GenericDao<WeaponType>,
        TypeCarrierDao<WeaponType>,
        WeaponCarrierDao<WeaponType>{
    
}
