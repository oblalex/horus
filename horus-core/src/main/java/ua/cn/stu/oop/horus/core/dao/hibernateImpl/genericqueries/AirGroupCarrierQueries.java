package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupCarrier;

/**
 *
 * @author alex
 */
public class AirGroupCarrierQueries {
    private static final String GET_CARRIERS_BY_AIR_GROUP = "FROM %s WHERE airGroup = ?";

    public static String getCarriersByAirGroup(Class<? extends AirGroupCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_AIR_GROUP, carrierClass.getName());
    }   
}
