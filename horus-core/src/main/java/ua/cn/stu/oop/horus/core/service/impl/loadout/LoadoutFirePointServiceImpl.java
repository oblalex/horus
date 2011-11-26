package ua.cn.stu.oop.horus.core.service.impl.loadout;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout.LoadoutFirePointDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.loadout.*;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.loadout.LoadoutFirePointService;

/**
 *
 * @author alex
 */
@Service("loadoutFirePointService")
public class LoadoutFirePointServiceImpl
        extends GenericServiceImpl<LoadoutFirePoint, LoadoutFirePointDaoHibernateImpl>
        implements LoadoutFirePointService {

    @Autowired
    public LoadoutFirePointServiceImpl(LoadoutFirePointDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<LoadoutFirePoint> getEntitiesByLoadout(LoadoutEntity loadout) {
        return (Collection<LoadoutFirePoint>) LoadoutCarrierServiceImpl.getEntitiesByLoadoutFromDao(loadout, dao);
    }
}
