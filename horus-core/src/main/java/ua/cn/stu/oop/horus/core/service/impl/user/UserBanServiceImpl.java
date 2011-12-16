package ua.cn.stu.oop.horus.core.service.impl.user;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.UserBanDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.UserBanService;

/**
 *
 * @author alex
 */
@Service("userBanService")
public class UserBanServiceImpl
        extends GenericServiceImpl<UserBan, UserBanDaoHibernateImpl>
        implements UserBanService {

    @Autowired
    public UserBanServiceImpl(UserBanDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<UserBan> getEntitiesByUser(User user) {
        return (Collection<UserBan>) UserCarrierServiceImpl.getEntitiesByUserFromDao(user, dao);
    }

    @Override
    public UserBan getCurrentUserBanOrNull(User user) {
        return dao.getCurrentUserBanOrNull(user);
    }

    @Override
    public Boolean isUserBannedNow(User user) {
        return dao.isUserBannedNow(user);
    }

    @Override
    public Collection<UserBan> getAllSortedByUserLogin() {
        return (Collection<UserBan>) UserCarrierServiceImpl.getAllSortedByUserLogin(dao);
    }
}
