package ua.cn.stu.oop.horus.core.dao.user;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.user.*;

/**
 *
 * @author alex
 */
public interface UserAdminDao
        extends GenericDao<UserAdmin>,
        UserCarrierDao<UserAdmin> {

    public boolean noAdminExists();
    public UserAdmin getAdminOrNullByUser(User user);
}
