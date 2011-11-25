package ua.cn.stu.oop.horus.core.dao.user;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.user.UserAdmin;

/**
 *
 * @author alex
 */
public interface UserAdminDao 
        extends GenericDao<UserAdmin>,
        UserCarrierDao<UserAdmin>{
    public boolean noAdminExists();
}
