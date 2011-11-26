package ua.cn.stu.oop.horus.core.service.impl.territory;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.territory.NodeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.Node;
import ua.cn.stu.oop.horus.core.domain.territory.NodeCarrier;

/**
 *
 * @author alex
 */
public class NodeCarrierServiceImpl {
    
    public static <E extends NodeCarrier> E getEntityOrNullByNodeFromDao(
            Node node,
            NodeCarrierDao<E> dao) {
        return dao.getEntityOrNullByNode(node);
    }
}
