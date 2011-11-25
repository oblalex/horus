package ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.LoadoutCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.loadout.AircraftLoadoutDao;
import ua.cn.stu.oop.horus.core.domain.loadout.*;

/**
 *
 * @author alex
 */
@Repository("AircraftLoadoutDaoHibernateImpl")
public class AircraftLoadoutDaoHibernateImpl
        extends GenericDaoHibernateImpl<AircraftLoadout>
        implements AircraftLoadoutDao {

    public AircraftLoadoutDaoHibernateImpl() {
        super(AircraftLoadout.class);
    }

    @Override
    public Collection<AircraftLoadout> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        Class cls = getEntityClass();
        String query = LoadoutCarrierQueries.
                getCarrierByLoadout(cls);
        return multipleResultByQuery(query, loadoutEntity);
    }
}
