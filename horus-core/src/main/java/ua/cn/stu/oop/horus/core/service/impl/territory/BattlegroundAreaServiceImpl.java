package ua.cn.stu.oop.horus.core.service.impl.territory;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.BattlegroundAreaDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.BattlegroundAreaService;

/**
 *
 * @author alex
 */
@Service("battlegroundAreaService")
public class BattlegroundAreaServiceImpl
        extends GenericServiceImpl<BattlegroundArea, BattlegroundAreaDaoHibernateImpl>
        implements BattlegroundAreaService {

    @Autowired
    public BattlegroundAreaServiceImpl(BattlegroundAreaDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public BattlegroundArea getEntityOrNullByNode(Node node) {
        return NodeCarrierServiceImpl.getEntityOrNullByNodeFromDao(node, dao);
    }

    @Override
    public Collection<BattlegroundArea> getEntitiesByType(EntityType type) {
        return (Collection<BattlegroundArea>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
