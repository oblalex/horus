package ua.cn.stu.oop.horus.core.dao;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.ParentCarrier;

/**
 *
 * @author alex
 */
public interface ParentCarrierDao<E extends ParentCarrier> {
    
    public Collection<E> getChildrenOfParent(E parent);
}
