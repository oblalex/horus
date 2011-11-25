package ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.gameflow.MissionEventDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TypeCarrierQueries;
import ua.cn.stu.oop.horus.core.domain.gameflow.MissionEvent;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("MissionEventDaoHibernateImpl")
public class MissionEventDaoHibernateImpl
        extends GenericDaoHibernateImpl<MissionEvent>
        implements MissionEventDao {

    public MissionEventDaoHibernateImpl() {
        super(MissionEvent.class);
    }

    @Override
    public Collection<MissionEvent> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }
}
