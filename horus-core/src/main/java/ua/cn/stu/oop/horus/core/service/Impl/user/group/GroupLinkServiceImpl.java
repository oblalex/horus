package ua.cn.stu.oop.horus.core.service.Impl.user.group;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.GroupLinkDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupLink;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.GroupLinkService;

/**
 *
 * @author alex
 */
@Service("groupLinkService")
public class GroupLinkServiceImpl
        extends GenericServiceImpl<GroupLink, GroupLinkDaoHibernateImpl>
        implements GroupLinkService {

    @Autowired
    public GroupLinkServiceImpl(GroupLinkDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<GroupLink> getEntitiesByType(EntityType type) {
        return (Collection<GroupLink>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
