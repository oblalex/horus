package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigSchemeCarrier;

/**
 *
 * @author alex
 */
public class ConfigSchemeCarrierQueries {
    private static final String GET_CARRIERS_BY_SCHEME = "FROM %s WHERE configScheme = ?";

    public static String getCarriersBySeries(Class<? extends ConfigSchemeCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_SCHEME, carrierClass.getName());
    }
}
