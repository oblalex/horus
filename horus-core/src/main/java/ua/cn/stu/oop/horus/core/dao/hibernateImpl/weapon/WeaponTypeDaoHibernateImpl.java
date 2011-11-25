package ua.cn.stu.oop.horus.core.dao.hibernateImpl.weapon;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.weapon.WeaponTypeDao;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.weapon.*;

/**
 *
 * @author alex
 */
@Repository("WeaponTypeDaoHibernateImpl")
public class WeaponTypeDaoHibernateImpl
        extends GenericDaoHibernateImpl<WeaponType>
        implements WeaponTypeDao {

    public WeaponTypeDaoHibernateImpl() {
        super(WeaponType.class);
    }

    @Override
    public Collection<WeaponType> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }

    @Override
    public Collection<WeaponType> getEntitiesByWeapon(Weapon weapon) {
        Class cls = getEntityClass();
        String query = WeaponCarrierQueries.
                getCarriersByWeapon(cls);
        return multipleResultByQuery(query, weapon);
    }    
}
