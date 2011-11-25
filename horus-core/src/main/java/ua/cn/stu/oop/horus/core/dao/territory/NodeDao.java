package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.Node;

/**
 *
 * @author alex
 */
public interface NodeDao 
        extends GenericDao<Node>,
        TypeCarrierDao<Node>{
}
