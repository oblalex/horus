package ua.cn.stu.oop.horus.core.service.impl.user.group;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.GroupEnterRequestDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupEnterRequest;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupLink;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.user.UserCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.GroupEnterRequestService;

/**
 *
 * @author alex
 */
@Service("groupEnterRequestService")
public class GroupEnterRequestServiceImpl
        extends GenericServiceImpl<GroupEnterRequest, GroupEnterRequestDaoHibernateImpl>
        implements GroupEnterRequestService {

    @Autowired
    public GroupEnterRequestServiceImpl(GroupEnterRequestDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<GroupEnterRequest> getEntitiesByType(EntityType type) {
        return (Collection<GroupEnterRequest>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }

    @Override
    public Collection<GroupEnterRequest> getEntitiesByUser(User user) {
        return (Collection<GroupEnterRequest>) UserCarrierServiceImpl.getEntitiesByUserFromDao(user, dao);
    }

    @Override
    public Collection<GroupEnterRequest> getEntitiesByGroupLink(GroupLink groupLink) {
        return (Collection<GroupEnterRequest>) GroupLinkCarrierServiceImpl.getEntitiesByGroupLinkFromDao(groupLink, dao);
    }
}
