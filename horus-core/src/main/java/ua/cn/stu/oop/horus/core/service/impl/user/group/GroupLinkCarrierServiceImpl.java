package ua.cn.stu.oop.horus.core.service.impl.user.group;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.user.group.GroupLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupLink;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupLinkCarrier;

/**
 *
 * @author alex
 */
public class GroupLinkCarrierServiceImpl {
    
    public static Collection<? extends GroupLinkCarrier> getEntitiesByGroupLinkFromDao(
            GroupLink groupLink,
            GroupLinkCarrierDao<? extends GroupLinkCarrier> dao) {
        return dao.getEntitiesByGroupLink(groupLink);
    }
}
