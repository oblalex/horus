package ua.cn.stu.oop.horus.core.service.impl;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.ParentCarrierDao;
import ua.cn.stu.oop.horus.core.domain.ParentCarrier;

/**
 *
 * @author alex
 */
public class ParentCarrierServiceImpl {

    public static Collection<? extends ParentCarrier> getChildrenOfParentFromDao(
            ParentCarrier parent,
            ParentCarrierDao dao) {
        return dao.getChildrenOfParent(parent);
    }
}
