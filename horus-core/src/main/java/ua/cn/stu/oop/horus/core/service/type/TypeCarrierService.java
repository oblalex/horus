package ua.cn.stu.oop.horus.core.service.type;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public interface TypeCarrierService<E extends TypeCarrier> {

    public Collection<E> getEntitiesByType(EntityType type);
}
