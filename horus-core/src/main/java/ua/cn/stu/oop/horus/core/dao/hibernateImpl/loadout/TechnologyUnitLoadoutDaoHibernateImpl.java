package ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.LoadoutCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.loadout.TechnologyUnitLoadoutDao;
import ua.cn.stu.oop.horus.core.domain.loadout.*;

/**
 *
 * @author alex
 */
@Repository("TechnologyUnitLoadoutDaoHibernateImpl")
public class TechnologyUnitLoadoutDaoHibernateImpl
        extends GenericDaoHibernateImpl<TechnologyUnitLoadout>
        implements TechnologyUnitLoadoutDao {

    public TechnologyUnitLoadoutDaoHibernateImpl() {
        super(TechnologyUnitLoadout.class);
    }

    @Override
    public Collection<TechnologyUnitLoadout> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        Class cls = getEntityClass();
        String query = LoadoutCarrierQueries.
                getCarrierByLoadout(cls);
        return multipleResultByQuery(query, loadoutEntity);
    }
    
}
