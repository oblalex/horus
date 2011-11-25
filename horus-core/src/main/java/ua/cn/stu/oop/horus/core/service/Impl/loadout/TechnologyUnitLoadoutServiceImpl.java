package ua.cn.stu.oop.horus.core.service.Impl.loadout;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout.TechnologyUnitLoadoutDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.loadout.LoadoutEntity;
import ua.cn.stu.oop.horus.core.domain.loadout.TechnologyUnitLoadout;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.loadout.TechnologyUnitLoadoutService;

/**
 *
 * @author alex
 */
@Service("technologyUnitLoadoutService")
public class TechnologyUnitLoadoutServiceImpl
        extends GenericServiceImpl<TechnologyUnitLoadout, TechnologyUnitLoadoutDaoHibernateImpl>
        implements TechnologyUnitLoadoutService {

    @Autowired
    public TechnologyUnitLoadoutServiceImpl(TechnologyUnitLoadoutDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TechnologyUnitLoadout> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        return (Collection<TechnologyUnitLoadout>) LoadoutCarrierServiceImpl.getEntitiesByLoadoutFromDao(loadoutEntity, dao);
    }
}
