package ua.cn.stu.oop.horus.core.dao.hibernateImpl.object;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TypeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.object.BuildingDao;
import ua.cn.stu.oop.horus.core.domain.object.Building;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("BuildingDaoHibernateImpl")
public class BuildingDaoHibernateImpl 
        extends GenericDaoHibernateImpl<Building>
        implements BuildingDao {
    
    public BuildingDaoHibernateImpl() {
        super(Building.class);
    }

    @Override
    public Collection<Building> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }
}
