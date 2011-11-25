package ua.cn.stu.oop.horus.core.service.user;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.user.*;

/**
 *
 * @author alex
 */
public interface UserCarrierService<E extends UserCarrier> {

    public Collection<E> getEntitiesByUser(User user);
}
