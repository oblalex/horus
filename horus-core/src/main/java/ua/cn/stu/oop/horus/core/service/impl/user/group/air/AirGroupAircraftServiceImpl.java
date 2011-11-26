package ua.cn.stu.oop.horus.core.service.impl.user.group.air;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air.AirGroupAircraftDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupAircraft;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirGroupAircraftService;

/**
 *
 * @author alex
 */
@Service("airGroupAircraftService")
public class AirGroupAircraftServiceImpl
        extends GenericServiceImpl<AirGroupAircraft, AirGroupAircraftDaoHibernateImpl>
        implements AirGroupAircraftService {

    @Autowired
    public AirGroupAircraftServiceImpl(AirGroupAircraftDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<AirGroupAircraft> getEntitiesByAirGroup(AirGroup airGroup) {
        return (Collection<AirGroupAircraft>) AirGroupCarrierServiceImpl.getEntitiesByAirGroupFromDao(airGroup, dao);
    }
}
