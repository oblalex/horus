package ua.cn.stu.oop.horus.core.service.Impl.loadout;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout.TechnologyUnitLoadoutLockInfoDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.loadout.LoadoutEntity;
import ua.cn.stu.oop.horus.core.domain.loadout.TechnologyUnitLoadoutLockInfo;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.loadout.TechnologyUnitLoadoutLockInfoService;

/**
 *
 * @author alex
 */
@Service("technologyUnitLoadoutLockInfoService")
public class TechnologyUnitLoadoutLockInfoServiceImpl
        extends GenericServiceImpl<TechnologyUnitLoadoutLockInfo, TechnologyUnitLoadoutLockInfoDaoHibernateImpl>
        implements TechnologyUnitLoadoutLockInfoService {

    @Autowired
    public TechnologyUnitLoadoutLockInfoServiceImpl(TechnologyUnitLoadoutLockInfoDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TechnologyUnitLoadoutLockInfo> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        return (Collection<TechnologyUnitLoadoutLockInfo>) LoadoutCarrierServiceImpl.getEntitiesByLoadoutFromDao(loadoutEntity, dao);
    }
}
