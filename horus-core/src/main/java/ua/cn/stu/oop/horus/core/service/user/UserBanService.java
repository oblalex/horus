package ua.cn.stu.oop.horus.core.service.user;

import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.domain.user.UserBan;

/**
 *
 * @author alex
 */
public interface UserBanService
        extends GenericService<UserBan>,
        UserCarrierService<UserBan> {
    
    public UserBan getCurrentUserBanOrNull(User user);
    public Boolean isUserBannedNow(User user);
}
