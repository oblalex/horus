package ua.cn.stu.oop.horus.core.service.user;

import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.service.GenericService;

/**
 *
 * @author alex
 */
public interface UserAdminService
        extends GenericService<UserAdmin>,
        UserCarrierService<UserAdmin>{
    
    public boolean noAdminExists();
    public UserAdmin getAdminOrNullByUser(User user);
}
