package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.territory.BattlegroundAreaDao;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("BattlegroundAreaDaoHibernateImpl")
public class BattlegroundAreaDaoHibernateImpl
        extends GenericDaoHibernateImpl<BattlegroundArea>
        implements BattlegroundAreaDao {
    
    public BattlegroundAreaDaoHibernateImpl() {
        super(BattlegroundArea.class);
    }

    @Override
    public BattlegroundArea getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (BattlegroundArea) singleResultOrNullByQuery(query, node);
    }

    @Override
    public Collection<BattlegroundArea> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }    
}
