package ua.cn.stu.oop.horus.core.service.Impl.gameflow;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow.MissionEventDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.MissionEvent;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.gameflow.MissionEventService;

/**
 *
 * @author alex
 */
@Service("missionEventService")
public class MissionEventServiceImpl
        extends GenericServiceImpl<MissionEvent, MissionEventDaoHibernateImpl>
        implements MissionEventService {

    @Autowired
    public MissionEventServiceImpl(MissionEventDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<MissionEvent> getEntitiesByType(EntityType type) {
        return (Collection<MissionEvent>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
