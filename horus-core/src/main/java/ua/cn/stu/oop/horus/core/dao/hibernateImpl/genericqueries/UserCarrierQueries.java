package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.user.UserCarrier;

/**
 *
 * @author alex
 */
public class UserCarrierQueries {
    private static final String GET_CARRIERS_BY_USER = "FROM %s WHERE user = ?";
    private static final String GET_ALL_SORTED_BY_USER_LOGIN = "FROM %s ucarrier WHERE (ucarrier.user is not null) ORDER BY ucarrier.user.login";

    public static String getCarriersByUser(Class<? extends UserCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_USER, carrierClass.getName());
    }
    
    public static String getAllSortedByUserLogin(Class<? extends UserCarrier> carrierClass) {
        return String.format(GET_ALL_SORTED_BY_USER_LOGIN, carrierClass.getName());
    }
}
