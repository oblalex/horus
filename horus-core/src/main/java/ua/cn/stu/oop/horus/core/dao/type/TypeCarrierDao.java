package ua.cn.stu.oop.horus.core.dao.type;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public interface TypeCarrierDao<E extends TypeCarrier> {

    public Collection<E> getEntitiesByType(EntityType type);
}
