package ua.cn.stu.oop.horus.core.service.loadout;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.loadout.*;

/**
 *
 * @author alex
 */
public interface LoadoutCarrierService<E extends LoadoutCarrier> {

    public Collection<E> getEntitiesByLoadout(LoadoutEntity loadoutEntity);
    
}
