package ua.cn.stu.oop.horus.core.service.loadout;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.domain.loadout.AircraftLoadout;

/**
 *
 * @author alex
 */
public interface AircraftLoadoutService
        extends GenericService<AircraftLoadout>,
        LoadoutCarrierService<AircraftLoadout> {
}
