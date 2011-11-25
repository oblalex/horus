package ua.cn.stu.oop.horus.core.service.user.group;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.user.group.*;

/**
 *
 * @author alex
 */
public interface GroupLinkCarrierService<E extends GroupLinkCarrier> {

    public Collection<E> getEntitiesByGroupLink(GroupLink groupLink);
}
