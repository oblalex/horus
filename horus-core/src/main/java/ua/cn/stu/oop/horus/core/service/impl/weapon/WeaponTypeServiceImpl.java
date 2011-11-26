package ua.cn.stu.oop.horus.core.service.impl.weapon;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.weapon.WeaponTypeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;
import ua.cn.stu.oop.horus.core.domain.weapon.WeaponType;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.weapon.WeaponTypeService;

/**
 *
 * @author alex
 */
@Service("weaponTypeService")
public class WeaponTypeServiceImpl
        extends GenericServiceImpl<WeaponType, WeaponTypeDaoHibernateImpl>
        implements WeaponTypeService {

    @Autowired
    public WeaponTypeServiceImpl(WeaponTypeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<WeaponType> getEntitiesByType(EntityType type) {
        return (Collection<WeaponType>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }

    @Override
    public Collection<WeaponType> getEntitiesByWeapon(Weapon weapon) {
        return (Collection<WeaponType>) WeaponCarrierServiceImpl.getEntitiesByWeaponFromDao(weapon, dao);
    }
}
