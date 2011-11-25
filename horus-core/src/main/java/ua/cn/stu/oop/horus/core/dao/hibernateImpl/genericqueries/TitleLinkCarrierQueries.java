package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.text.TitleLinkCarrier;

/**
 *
 * @author alex
 */
public class TitleLinkCarrierQueries {
    private static final String GET_ALL_TITLE_LINKS = "SELECT titleLink FROM %s";
    private static final String GET_CARRIERS_BY_TITLE_LINK = "FROM %s WHERE titleLink = ?";

    public static String getAllTitleLinks(Class<? extends TitleLinkCarrier> carrierClass) {
        return String.format(GET_ALL_TITLE_LINKS, carrierClass.getName());
    }
    
    public static String getCarriersByTitleLink(Class<? extends TitleLinkCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_TITLE_LINK, carrierClass.getName());
    }
}
