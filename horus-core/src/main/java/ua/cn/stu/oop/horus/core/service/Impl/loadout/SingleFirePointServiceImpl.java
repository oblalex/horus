package ua.cn.stu.oop.horus.core.service.Impl.loadout;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout.SingleFirePointDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.loadout.SingleFirePoint;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.weapon.WeaponCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.loadout.SingleFirePointService;

/**
 *
 * @author alex
 */
@Service("singleFirePointService")
public class SingleFirePointServiceImpl
        extends GenericServiceImpl<SingleFirePoint, SingleFirePointDaoHibernateImpl>
        implements SingleFirePointService {

    @Autowired
    public SingleFirePointServiceImpl(SingleFirePointDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<SingleFirePoint> getEntitiesByWeapon(Weapon weapon) {
        return (Collection<SingleFirePoint>) WeaponCarrierServiceImpl.getEntitiesByWeaponFromDao(weapon, dao);
    }
}
