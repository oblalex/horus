package ua.cn.stu.oop.horus.core.service.Impl.user.group.air;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupCarrierDao;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupCarrier;

/**
 *
 * @author alex
 */
public class AirGroupCarrierServiceImpl {
    
    public static Collection<? extends AirGroupCarrier> getEntitiesByAirGroupFromDao(
            AirGroup airGroup,
            AirGroupCarrierDao<? extends AirGroupCarrier> dao) {
        return dao.getEntitiesByAirGroup(airGroup);
    }
}
