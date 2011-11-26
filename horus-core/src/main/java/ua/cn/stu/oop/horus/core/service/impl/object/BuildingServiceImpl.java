package ua.cn.stu.oop.horus.core.service.impl.object;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.object.BuildingDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.object.Building;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.object.BuildingService;

/**
 *
 * @author alex
 */
@Service("buildingService")
public class BuildingServiceImpl
        extends GenericServiceImpl<Building, BuildingDaoHibernateImpl>
        implements BuildingService {

    @Autowired
    public BuildingServiceImpl(BuildingDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<Building> getEntitiesByType(EntityType type) {
        return (Collection<Building>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
