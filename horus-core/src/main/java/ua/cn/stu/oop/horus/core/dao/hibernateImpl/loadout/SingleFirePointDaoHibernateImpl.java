package ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.WeaponCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.loadout.SingleFirePointDao;
import ua.cn.stu.oop.horus.core.domain.loadout.SingleFirePoint;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;

/**
 *
 * @author alex
 */
@Repository("SingleFirePointDaoHibernateImpl")
public class SingleFirePointDaoHibernateImpl
        extends GenericDaoHibernateImpl<SingleFirePoint>
        implements SingleFirePointDao {

    public SingleFirePointDaoHibernateImpl() {
        super(SingleFirePoint.class);
    }

    @Override
    public Collection<SingleFirePoint> getEntitiesByWeapon(Weapon weapon) {
        Class cls = getEntityClass();
        String query = WeaponCarrierQueries.
                getCarriersByWeapon(cls);
        return multipleResultByQuery(query, weapon);
    }    
}
