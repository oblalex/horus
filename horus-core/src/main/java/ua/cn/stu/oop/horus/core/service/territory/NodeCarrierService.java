package ua.cn.stu.oop.horus.core.service.territory;

import ua.cn.stu.oop.horus.core.domain.territory.*;

/**
 *
 * @author alex
 */
public interface NodeCarrierService<E extends NodeCarrier> {

    public E getEntityOrNullByNode(Node node);
}
