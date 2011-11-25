package ua.cn.stu.oop.horus.core.service.user;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.domain.user.UserAdmin;

/**
 *
 * @author alex
 */
public interface UserAdminService
        extends GenericService<UserAdmin>,
        UserCarrierService<UserAdmin>{
    public boolean noAdminExists();
}
