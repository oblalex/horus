package ua.cn.stu.oop.horus.core.dao.user.group.air;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.user.group.air.*;

/**
 *
 * @author alex
 */
public interface AirGroupCarrierDao<E extends AirGroupCarrier> {
    
    public Collection<E> getEntitiesByAirGroup(AirGroup airGroup);
}
