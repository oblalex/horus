package ua.cn.stu.oop.horus.core.service.Impl.loadout;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.loadout.LoadoutCarrierDao;
import ua.cn.stu.oop.horus.core.domain.loadout.*;

/**
 *
 * @author alex
 */
public class LoadoutCarrierServiceImpl {

    public static Collection<? extends LoadoutCarrier> getEntitiesByLoadoutFromDao(
            LoadoutEntity loadoutEntity,
            LoadoutCarrierDao<? extends LoadoutCarrier> dao) {
        return dao.getEntitiesByLoadout(loadoutEntity);
    }
}
