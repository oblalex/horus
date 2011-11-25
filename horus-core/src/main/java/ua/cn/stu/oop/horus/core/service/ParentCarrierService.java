package ua.cn.stu.oop.horus.core.service;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.ParentCarrier;

/**
 *
 * @author alex
 */
public interface ParentCarrierService<E extends ParentCarrier> {

    public Collection<E> getChildrenOfParent(E parent);
}
