package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.UserCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.UserBanDao;
import ua.cn.stu.oop.horus.core.domain.user.*;

/**
 *
 * @author alex
 */
@Repository("UserBanDaoHibernateImpl")
public class UserBanDaoHibernateImpl
        extends GenericDaoHibernateImpl<UserBan>
        implements UserBanDao {

    public UserBanDaoHibernateImpl() {
        super(UserBan.class);
    }

    @Override
    public Collection<UserBan> getEntitiesByUser(User user) {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getCarriersByUser(cls);
        return multipleResultByQuery(query, user);
    }

    @Override
    public UserBan getCurrentUserBanOrNull(User user) {
        return (UserBan) singleResultOrNullByNamedQuery(
                "findUserCurrentBanOrNullByUserIdNativeSQL",
                user.getId());
    }

    @Override
    public Boolean isUserBannedNow(User user) {
        return (Boolean) requiredSingleResultByNamedQuery(
                "isUserBannedNowNativeSQL",
                user.getId());
    }
}
