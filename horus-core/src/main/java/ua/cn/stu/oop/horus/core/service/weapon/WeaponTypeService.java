package ua.cn.stu.oop.horus.core.service.weapon;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.type.TypeCarrierService;
import ua.cn.stu.oop.horus.core.domain.weapon.WeaponType;

/**
 *
 * @author alex
 */
public interface WeaponTypeService
        extends GenericService<WeaponType>,
        TypeCarrierService<WeaponType>,
        WeaponCarrierService<WeaponType> {
}
