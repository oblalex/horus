package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.series.SeriesCarrier;

/**
 *
 * @author alex
 */
public class SeriesCarrierQueries {
    
    private static final String GET_CARRIERS_BY_SERIES = "FROM %s WHERE series = ?";

    public static String getCarrierBySeries(Class<? extends SeriesCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_SERIES, carrierClass.getName());
    }
}
