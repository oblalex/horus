package ua.cn.stu.oop.horus.core.service.impl.territory;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.RoadDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.RoadService;

/**
 *
 * @author alex
 */
@Service("roadService")
public class RoadServiceImpl
        extends GenericServiceImpl<Road, RoadDaoHibernateImpl>
        implements RoadService {

    @Autowired
    public RoadServiceImpl(RoadDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Road getEntityOrNullByNode(Node node) {
        return NodeCarrierServiceImpl.getEntityOrNullByNodeFromDao(node, dao);
    }

    @Override
    public Collection<Road> getEntitiesByType(EntityType type) {
        return (Collection<Road>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
