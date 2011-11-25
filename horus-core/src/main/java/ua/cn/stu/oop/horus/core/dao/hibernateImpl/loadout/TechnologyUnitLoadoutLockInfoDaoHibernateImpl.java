package ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.LoadoutCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.loadout.TechnologyUnitLoadoutLockInfoDao;
import ua.cn.stu.oop.horus.core.domain.loadout.*;

/**
 *
 * @author alex
 */
@Repository("TechnologyUnitLoadoutLockInfoDaoHibernateImpl")
public class TechnologyUnitLoadoutLockInfoDaoHibernateImpl
        extends GenericDaoHibernateImpl<TechnologyUnitLoadoutLockInfo>
        implements TechnologyUnitLoadoutLockInfoDao {

    public TechnologyUnitLoadoutLockInfoDaoHibernateImpl() {
        super(TechnologyUnitLoadoutLockInfo.class);
    }

    @Override
    public Collection<TechnologyUnitLoadoutLockInfo> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        Class cls = getEntityClass();
        String query = LoadoutCarrierQueries.
                getCarrierByLoadout(cls);
        return multipleResultByQuery(query, loadoutEntity);
    }
}
