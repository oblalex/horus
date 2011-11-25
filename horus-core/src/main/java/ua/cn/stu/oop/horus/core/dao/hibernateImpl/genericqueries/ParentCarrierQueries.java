package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.ParentCarrier;

/**
 *
 * @author alex
 */
public class ParentCarrierQueries {
    
    public static final String PARAMETER_PARENT = "parent";
    
    private static final String GET_CHILDREN_BY_PARENT = 
            "FROM %s parentCarrier"
            + " WHERE ( parentCarrier.parent = :"+PARAMETER_PARENT
            + " OR ( parentCarrier.parent IS NULL"
            + " AND :"+PARAMETER_PARENT+" IS NULL ))";
    
    public static String getChildrenByParent(Class<? extends ParentCarrier> carrierClass) {
        return String.format(GET_CHILDREN_BY_PARENT, carrierClass.getName());
    }
}
