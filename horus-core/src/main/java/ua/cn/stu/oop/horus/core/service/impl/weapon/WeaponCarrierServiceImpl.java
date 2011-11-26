package ua.cn.stu.oop.horus.core.service.impl.weapon;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.weapon.WeaponCarrierDao;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;
import ua.cn.stu.oop.horus.core.domain.weapon.WeaponCarrier;

/**
 *
 * @author alex
 */
public class WeaponCarrierServiceImpl {
    
    public static Collection<? extends WeaponCarrier> getEntitiesByWeaponFromDao(
            Weapon weapon,
            WeaponCarrierDao<? extends WeaponCarrier> dao) {
        return dao.getEntitiesByWeapon(weapon);
    }
}
