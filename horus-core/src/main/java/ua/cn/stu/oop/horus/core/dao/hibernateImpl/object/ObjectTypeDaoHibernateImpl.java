package ua.cn.stu.oop.horus.core.dao.hibernateImpl.object;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TypeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.object.ObjectTypeDao;
import ua.cn.stu.oop.horus.core.domain.object.ObjectType;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("ObjectTypeDaoHibernateImpl")
public class ObjectTypeDaoHibernateImpl
        extends GenericDaoHibernateImpl<ObjectType>
        implements ObjectTypeDao {

    public ObjectTypeDaoHibernateImpl() {
        super(ObjectType.class);
    }

    @Override
    public Collection<ObjectType> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }
}
