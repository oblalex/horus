package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class CommonQueries {

    private static final String GET_ALL_ENTITIES = "FROM %s";

    public static String getAllEntities(Class<? extends GenericDomain> entityClass) {
        return String.format(GET_ALL_ENTITIES, entityClass.getName());
    }
}
