package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TypeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupStatusDao;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupStatus;

/**
 *
 * @author alex
 */
@Repository("AirGroupStatusDaoHibernateImpl")
public class AirGroupStatusDaoHibernateImpl
        extends GenericDaoHibernateImpl<AirGroupStatus>
        implements AirGroupStatusDao {

    public AirGroupStatusDaoHibernateImpl() {
        super(AirGroupStatus.class);
    }

    @Override
    public Collection<AirGroupStatus> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }    
}
