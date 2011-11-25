package ua.cn.stu.oop.horus.core.service.weapon;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.weapon.*;

/**
 *
 * @author alex
 */
public interface WeaponCarrierService<E extends WeaponCarrier> {

    public Collection<E> getEntitiesByWeapon(Weapon weapon);
}
