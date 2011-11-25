package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.territory.NodeCarrier;

/**
 *
 * @author alex
 */
public class NodeCarrierQueries {
    private static final String GET_CARRIERS_BY_NODE = "FROM %s WHERE node = ?";
    
    public static String getCarriersByNode(Class<? extends NodeCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_NODE, carrierClass.getName());
    }
}
