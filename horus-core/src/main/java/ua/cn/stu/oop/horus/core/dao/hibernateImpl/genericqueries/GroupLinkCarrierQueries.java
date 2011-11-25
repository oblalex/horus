package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.user.group.GroupLinkCarrier;

/**
 *
 * @author alex
 */
public class GroupLinkCarrierQueries {
    private static final String GET_CARRIERS_BY_GROUP_LINK = "FROM %s WHERE groupLink = ?";

    public static String getCarriersByGroupLink(Class<? extends GroupLinkCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_GROUP_LINK, carrierClass.getName());
    }    
}
