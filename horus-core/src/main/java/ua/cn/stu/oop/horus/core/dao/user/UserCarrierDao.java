package ua.cn.stu.oop.horus.core.dao.user;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.user.*;

/**
 *
 * @author alex
 */
public interface UserCarrierDao<E extends UserCarrier> {

    public Collection<E> getEntitiesByUser(User user);
}
