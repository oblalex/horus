package ua.cn.stu.oop.horus.core.service.impl.user.group.air;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air.AirGroupSpecializationTypeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupSpecializationType;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirGroupSpecializationTypeService;

/**
 *
 * @author alex
 */
@Service("airGroupSpecializationTypeService")
public class AirGroupSpecializationTypeServiceImpl
        extends GenericServiceImpl<AirGroupSpecializationType, AirGroupSpecializationTypeDaoHibernateImpl>
        implements AirGroupSpecializationTypeService {

    @Autowired
    public AirGroupSpecializationTypeServiceImpl(AirGroupSpecializationTypeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<AirGroupSpecializationType> getEntitiesByType(EntityType type) {
        return (Collection<AirGroupSpecializationType>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
