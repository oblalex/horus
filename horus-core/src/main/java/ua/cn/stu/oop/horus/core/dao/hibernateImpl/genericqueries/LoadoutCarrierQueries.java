package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.loadout.LoadoutCarrier;

/**
 *
 * @author alex
 */
public class LoadoutCarrierQueries {
    private static final String GET_CARRIERS_BY_LOADOUT = "FROM %s WHERE loadout = ?";

    public static String getCarrierByLoadout(Class<? extends LoadoutCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_LOADOUT, carrierClass.getName());
    }
}
