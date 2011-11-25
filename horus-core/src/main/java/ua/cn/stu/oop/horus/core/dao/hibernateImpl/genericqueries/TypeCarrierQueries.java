package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.type.TypeCarrier;

/**
 *
 * @author alex
 */
public class TypeCarrierQueries {

    private static final String GET_CARRIERS_BY_TYPE = "FROM %s WHERE type = ?";

    public static String getCarriersByType(Class<? extends TypeCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_TYPE, carrierClass.getName());
    }
}
