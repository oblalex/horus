package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.NodeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.territory.RoadSectionDao;
import ua.cn.stu.oop.horus.core.domain.territory.*;

/**
 *
 * @author alex
 */
@Repository("RoadSectionDaoHibernateImpl")
public class RoadSectionDaoHibernateImpl
        extends GenericDaoHibernateImpl<RoadSection>
        implements RoadSectionDao {
    
    public RoadSectionDaoHibernateImpl() {
        super(RoadSection.class);
    }

    @Override
    public RoadSection getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (RoadSection) singleResultOrNullByQuery(query, node);
    }
}
