package ua.cn.stu.oop.horus.core.service.weapon;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.series.SeriesCarrierService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;

/**
 *
 * @author alex
 */
public interface WeaponService
        extends GenericService<Weapon>,
        TitleLinkCarrierService<Weapon>,
        SeriesCarrierService<Weapon> {
}
