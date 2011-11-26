package ua.cn.stu.oop.horus.core.service.impl.user.group.air;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air.AirGroupStatusDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupStatus;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirGroupStatusService;

/**
 *
 * @author alex
 */
@Service("airGroupStatusService")
public class AirGroupStatusServiceImpl
        extends GenericServiceImpl<AirGroupStatus, AirGroupStatusDaoHibernateImpl>
        implements AirGroupStatusService {

    @Autowired
    public AirGroupStatusServiceImpl(AirGroupStatusDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<AirGroupStatus> getEntitiesByType(EntityType type) {
        return (Collection<AirGroupStatus>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
