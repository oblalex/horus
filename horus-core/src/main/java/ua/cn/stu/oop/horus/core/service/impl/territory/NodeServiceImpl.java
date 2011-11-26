package ua.cn.stu.oop.horus.core.service.impl.territory;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.NodeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.Node;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.NodeService;

/**
 *
 * @author alex
 */
@Service("nodeService")
public class NodeServiceImpl
        extends GenericServiceImpl<Node, NodeDaoHibernateImpl>
        implements NodeService {

    @Autowired
    public NodeServiceImpl(NodeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<Node> getEntitiesByType(EntityType type) {
        return (Collection<Node>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
