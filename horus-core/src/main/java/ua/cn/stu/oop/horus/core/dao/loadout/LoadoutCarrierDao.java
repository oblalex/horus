package ua.cn.stu.oop.horus.core.dao.loadout;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.loadout.*;

/**
 *
 * @author alex
 */
public interface LoadoutCarrierDao<E extends LoadoutCarrier> {

    public Collection<E> getEntitiesByLoadout(LoadoutEntity loadoutEntity);
    
}
