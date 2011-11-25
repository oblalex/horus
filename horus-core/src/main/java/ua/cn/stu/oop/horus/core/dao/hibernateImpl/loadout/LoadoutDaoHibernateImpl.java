package ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.ParentCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.loadout.LoadoutDao;
import ua.cn.stu.oop.horus.core.domain.loadout.Loadout;

/**
 *
 * @author alex
 */
@Repository("LoadoutDaoHibernateImpl")
public class LoadoutDaoHibernateImpl
        extends GenericDaoHibernateImpl<Loadout>
        implements LoadoutDao {

    public LoadoutDaoHibernateImpl() {
        super(Loadout.class);
    }

    @Override
    public Collection<Loadout> getChildrenOfParent(Loadout parent) {
        Class cls = getEntityClass();
        String query = ParentCarrierQueries.
                getChildrenByParent(cls);
        String paramTitle =
                ParentCarrierQueries.PARAMETER_PARENT;
        return multipleResultByQueryWithNamedParam(query, paramTitle, parent);
    }
    
}
