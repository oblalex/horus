package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.territory.RoadDao;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("RoadDaoHibernateImpl")
public class RoadDaoHibernateImpl
        extends GenericDaoHibernateImpl<Road>
        implements RoadDao {
    
    public RoadDaoHibernateImpl() {
        super(Road.class);
    }

    @Override
    public Road getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (Road) singleResultOrNullByQuery(query, node);
    }

    @Override
    public Collection<Road> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }    
}
