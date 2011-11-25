package ua.cn.stu.oop.horus.core.dao.weapon;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.weapon.*;

/**
 *
 * @author alex
 */
public interface WeaponCarrierDao<E extends WeaponCarrier> {

    public Collection<E> getEntitiesByWeapon(Weapon weapon);
    
}
