package ua.cn.stu.oop.horus.core.service.impl.loadout;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout.AircraftLoadoutDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.loadout.AircraftLoadout;
import ua.cn.stu.oop.horus.core.domain.loadout.LoadoutEntity;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.loadout.AircraftLoadoutService;

/**
 *
 * @author alex
 */
@Service("aircraftLoadoutService")
public class AircraftLoadoutServiceImpl
        extends GenericServiceImpl<AircraftLoadout, AircraftLoadoutDaoHibernateImpl>
        implements AircraftLoadoutService {

    @Autowired
    public AircraftLoadoutServiceImpl(AircraftLoadoutDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<AircraftLoadout> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        return (Collection<AircraftLoadout>) LoadoutCarrierServiceImpl.getEntitiesByLoadoutFromDao(loadoutEntity, dao);
    }
}
