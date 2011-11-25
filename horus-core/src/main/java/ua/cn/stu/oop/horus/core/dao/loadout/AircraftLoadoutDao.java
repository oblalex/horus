package ua.cn.stu.oop.horus.core.dao.loadout;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.loadout.AircraftLoadout;

/**
 *
 * @author alex
 */
public interface AircraftLoadoutDao
        extends GenericDao<AircraftLoadout>,
        LoadoutCarrierDao<AircraftLoadout>{
    
}
