package ua.cn.stu.oop.horus.core.dao.hibernateImpl.object;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TypeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.object.CertainObjectTypeDao;
import ua.cn.stu.oop.horus.core.domain.object.CertainObjectType;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("CertainObjectTypeDaoHibernateImpl")
public class CertainObjectTypeDaoHibernateImpl
        extends GenericDaoHibernateImpl<CertainObjectType>
        implements CertainObjectTypeDao {

    public CertainObjectTypeDaoHibernateImpl() {
        super(CertainObjectType.class);
    }

    @Override
    public Collection<CertainObjectType> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }
}
