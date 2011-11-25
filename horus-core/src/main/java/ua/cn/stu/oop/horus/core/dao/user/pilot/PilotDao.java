package ua.cn.stu.oop.horus.core.dao.user.pilot;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.user.UserCarrierDao;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupCarrierDao;
import ua.cn.stu.oop.horus.core.domain.user.pilot.Pilot;

/**
 *
 * @author alex
 */
public interface PilotDao
        extends GenericDao<Pilot>,
        AirGroupCarrierDao<Pilot>,
        UserCarrierDao<Pilot>{
    
}
