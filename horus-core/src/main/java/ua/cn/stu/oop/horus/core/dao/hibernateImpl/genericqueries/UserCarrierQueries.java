package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.user.UserCarrier;

/**
 *
 * @author alex
 */
public class UserCarrierQueries {
    private static final String GET_CARRIERS_BY_USER = "FROM %s WHERE user = ?";

    public static String getCarriersByUser(Class<? extends UserCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_USER, carrierClass.getName());
    }
}
