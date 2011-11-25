package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.AirGroupCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupAircraftDao;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupAircraft;

/**
 *
 * @author alex
 */
@Repository("AirGroupAircraftDaoHibernateImpl")
public class AirGroupAircraftDaoHibernateImpl
        extends GenericDaoHibernateImpl<AirGroupAircraft>
        implements AirGroupAircraftDao {

    public AirGroupAircraftDaoHibernateImpl() {
        super(AirGroupAircraft.class);
    }

    @Override
    public Collection<AirGroupAircraft> getEntitiesByAirGroup(AirGroup airGroup) {
        Class cls = getEntityClass();
        String query = AirGroupCarrierQueries.
                getCarriersByAirGroup(cls);
        return multipleResultByQuery(query, airGroup);
    }    
}
