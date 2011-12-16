package ua.cn.stu.oop.horus.core.service.impl.user.pilot;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.pilot.PilotDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;
import ua.cn.stu.oop.horus.core.domain.user.pilot.Pilot;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.user.UserCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.user.group.air.AirGroupCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.pilot.PilotService;

/**
 *
 * @author alex
 */
@Service("pilotService")
public class PilotServiceImpl
        extends GenericServiceImpl<Pilot, PilotDaoHibernateImpl>
        implements PilotService {

    @Autowired
    public PilotServiceImpl(PilotDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<Pilot> getEntitiesByAirGroup(AirGroup airGroup) {
        return (Collection<Pilot>) AirGroupCarrierServiceImpl.getEntitiesByAirGroupFromDao(airGroup, dao);
    }

    @Override
    public Collection<Pilot> getEntitiesByUser(User user) {
        return (Collection<Pilot>) UserCarrierServiceImpl.getEntitiesByUserFromDao(user, dao);
    }

    @Override
    public Collection<Pilot> getAllSortedByUserLogin() {
        return (Collection<Pilot>) UserCarrierServiceImpl.getAllSortedByUserLogin(dao);
    }
}
