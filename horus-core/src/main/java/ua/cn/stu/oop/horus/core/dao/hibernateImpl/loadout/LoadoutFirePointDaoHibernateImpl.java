package ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.LoadoutCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.loadout.LoadoutFirePointDao;
import ua.cn.stu.oop.horus.core.domain.loadout.*;

/**
 *
 * @author alex
 */
@Repository("LoadoutFirePointDaoHibernateImpl")
public class LoadoutFirePointDaoHibernateImpl
        extends GenericDaoHibernateImpl<LoadoutFirePoint>
        implements LoadoutFirePointDao {

    public LoadoutFirePointDaoHibernateImpl() {
        super(LoadoutFirePoint.class);
    }

    @Override
    public Collection<LoadoutFirePoint> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        Class cls = getEntityClass();
        String query = LoadoutCarrierQueries.
                getCarrierByLoadout(cls);
        return multipleResultByQuery(query, loadoutEntity);
    }    
}
