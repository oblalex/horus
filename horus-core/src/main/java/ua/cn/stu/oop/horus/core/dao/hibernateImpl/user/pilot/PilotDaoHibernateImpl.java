package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.pilot;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.user.pilot.PilotDao;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;
import ua.cn.stu.oop.horus.core.domain.user.pilot.Pilot;

/**
 *
 * @author alex
 */
@Repository("PilotDaoHibernateImpl")
public class PilotDaoHibernateImpl
        extends GenericDaoHibernateImpl<Pilot>
        implements PilotDao {

    public PilotDaoHibernateImpl() {
        super(Pilot.class);
    }

    @Override
    public Collection<Pilot> getEntitiesByAirGroup(AirGroup airGroup) {
        Class cls = getEntityClass();
        String query = AirGroupCarrierQueries.
                getCarriersByAirGroup(cls);
        return multipleResultByQuery(query, airGroup);
    }

    @Override
    public Collection<Pilot> getEntitiesByUser(User user) {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getCarriersByUser(cls);
        return multipleResultByQuery(query, user);
    }    
}
