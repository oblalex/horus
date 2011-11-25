package ua.cn.stu.oop.horus.core.service.user.pilot;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirGroupCarrierService;
import ua.cn.stu.oop.horus.core.domain.user.pilot.Pilot;
import ua.cn.stu.oop.horus.core.service.user.UserCarrierService;

/**
 *
 * @author alex
 */
public interface PilotService
        extends GenericService<Pilot>,
        AirGroupCarrierService<Pilot>,
        UserCarrierService<Pilot>{
}
