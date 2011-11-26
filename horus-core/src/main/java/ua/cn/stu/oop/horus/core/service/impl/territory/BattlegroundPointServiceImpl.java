package ua.cn.stu.oop.horus.core.service.impl.territory;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.BattlegroundPointDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.BattlegroundPointService;

/**
 *
 * @author alex
 */
@Service("battlegroundPointService")
public class BattlegroundPointServiceImpl
        extends GenericServiceImpl<BattlegroundPoint, BattlegroundPointDaoHibernateImpl>
        implements BattlegroundPointService {

    @Autowired
    public BattlegroundPointServiceImpl(BattlegroundPointDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public BattlegroundPoint getEntityOrNullByNode(Node node) {
        return NodeCarrierServiceImpl.getEntityOrNullByNodeFromDao(node, dao);
    }

    @Override
    public Collection<BattlegroundPoint> getEntitiesByType(EntityType type) {
        return (Collection<BattlegroundPoint>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
