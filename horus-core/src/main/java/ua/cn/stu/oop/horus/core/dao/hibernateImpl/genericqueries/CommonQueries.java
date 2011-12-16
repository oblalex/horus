package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class CommonQueries {

    private static final String GET_ALL_ENTITIES = "FROM %s";
    private static final String DELETE_BY_ID = "DELETE FROM %s where id=?";

    public static String getAllEntities(Class<? extends GenericDomain> entityClass) {
        return String.format(GET_ALL_ENTITIES, entityClass.getName());
    }
    
    public static String getDeleteById(Class<? extends GenericDomain> entityClass) {
        return String.format(DELETE_BY_ID, entityClass.getName());
    }
}
