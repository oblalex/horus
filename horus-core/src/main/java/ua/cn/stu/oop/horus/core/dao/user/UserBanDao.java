package ua.cn.stu.oop.horus.core.dao.user;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.UserBan;

/**
 *
 * @author alex
 */
public interface UserBanDao
        extends GenericDao<UserBan>,
        UserCarrierDao<UserBan> {

    public Boolean isUserBannedNow(User user);
    public UserBan getCurrentUserBanOrNull(User user);
}
