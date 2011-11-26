package ua.cn.stu.oop.horus.core.service.impl.user.group.air;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air.AirGroupDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupLink;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.text.StringTitleCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.user.group.GroupLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirGroupService;

/**
 *
 * @author alex
 */
@Service("airGroupService")
public class AirGroupServiceImpl
        extends GenericServiceImpl<AirGroup, AirGroupDaoHibernateImpl>
        implements AirGroupService {

    @Autowired
    public AirGroupServiceImpl(AirGroupDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<AirGroup> getEntitiesByType(EntityType type) {
        return (Collection<AirGroup>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }

    @Override
    public Collection<String> getAllTitles() {
        return StringTitleCarrierServiceImpl.getAllTitlesFromDao(dao);
    }

    @Override
    public AirGroup getEntityOrNullByTitle(String title) {
        return StringTitleCarrierServiceImpl.getEntityOrNullByTitleFromDao(title, dao);
    }

    @Override
    public Collection<AirGroup> getEntitiesByGroupLink(GroupLink groupLink) {
        return (Collection<AirGroup>) GroupLinkCarrierServiceImpl.getEntitiesByGroupLinkFromDao(groupLink, dao);
    }
}
