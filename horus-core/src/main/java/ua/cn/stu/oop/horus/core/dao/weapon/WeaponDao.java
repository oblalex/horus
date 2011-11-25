package ua.cn.stu.oop.horus.core.dao.weapon;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.series.SeriesCarrierDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;

/**
 *
 * @author alex
 */
public interface WeaponDao  
        extends GenericDao<Weapon>,
        TitleLinkCarrierDao<Weapon>,
        SeriesCarrierDao<Weapon>{
    
}
