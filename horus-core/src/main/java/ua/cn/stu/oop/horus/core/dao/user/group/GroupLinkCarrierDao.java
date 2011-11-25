package ua.cn.stu.oop.horus.core.dao.user.group;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.user.group.*;

/**
 *
 * @author alex
 */
public interface GroupLinkCarrierDao<E extends GroupLinkCarrier> {

    public Collection<E> getEntitiesByGroupLink(GroupLink groupLink);
}
