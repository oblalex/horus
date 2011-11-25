package ua.cn.stu.oop.horus.core.service.loadout;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.weapon.WeaponCarrierService;
import ua.cn.stu.oop.horus.core.domain.loadout.SingleFirePoint;

/**
 *
 * @author alex
 */
public interface SingleFirePointService
        extends GenericService<SingleFirePoint>,
        WeaponCarrierService<SingleFirePoint> {
}
