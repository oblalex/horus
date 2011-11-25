package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.territory.BattlegroundPointDao;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("BattlegroundPointDaoHibernateImpl")
public class BattlegroundPointDaoHibernateImpl
        extends GenericDaoHibernateImpl<BattlegroundPoint>
        implements BattlegroundPointDao {
    
    public BattlegroundPointDaoHibernateImpl() {
        super(BattlegroundPoint.class);
    }

    @Override
    public BattlegroundPoint getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (BattlegroundPoint) singleResultOrNullByQuery(query, node);
    }

    @Override
    public Collection<BattlegroundPoint> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }
}
