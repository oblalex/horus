package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TypeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupSpecializationTypeDao;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupSpecializationType;

/**
 *
 * @author alex
 */
@Repository("AirGroupSpecializationTypeDaoHibernateImpl")
public class AirGroupSpecializationTypeDaoHibernateImpl
        extends GenericDaoHibernateImpl<AirGroupSpecializationType>
        implements AirGroupSpecializationTypeDao {

    public AirGroupSpecializationTypeDaoHibernateImpl() {
        super(AirGroupSpecializationType.class);
    }

    @Override
    public Collection<AirGroupSpecializationType> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }    
}
