package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.domain.territory.*;

/**
 *
 * @author alex
 */
public interface NodeCarrierDao<E extends NodeCarrier> {

    public E getEntityOrNullByNode(Node node);
}
