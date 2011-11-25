package ua.cn.stu.oop.horus.core.dao.user.group;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.dao.user.UserCarrierDao;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupEnterRequest;

/**
 *
 * @author alex
 */
public interface GroupEnterRequestDao
        extends GenericDao<GroupEnterRequest>,
        TypeCarrierDao<GroupEnterRequest>,
        UserCarrierDao<GroupEnterRequest>,
        GroupLinkCarrierDao<GroupEnterRequest>{
    
}
