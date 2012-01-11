package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;

/**
 *
 * @author alex
 */
public class TitleCarrierQueries {

    private static final String GET_ALL_TITLES = "SELECT title FROM %s";
    private static final String GET_CARRIERS_BY_TITLE = "FROM %s WHERE title = '%s'";

    public static String getAllTitles(Class<? extends StringTitleCarrier> carrierClass) {
        return String.format(GET_ALL_TITLES, carrierClass.getName());
    }
    
    public static String getTitleCarriersByTitle(Class<? extends StringTitleCarrier> carrierClass, String title) {
        return String.format(GET_CARRIERS_BY_TITLE, carrierClass.getName(), title);
    }
}
