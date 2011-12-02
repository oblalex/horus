package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.UserCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.UserAdminDao;
import ua.cn.stu.oop.horus.core.domain.user.*;

/**
 *
 * @author alex
 */
@Repository("UserAdminDaoHibernateImpl")
public class UserAdminDaoHibernateImpl
        extends GenericDaoHibernateImpl<UserAdmin>
        implements UserAdminDao {

    public UserAdminDaoHibernateImpl() {
        super(UserAdmin.class);
    }

    @Override
    public Collection<UserAdmin> getEntitiesByUser(User user) {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getCarriersByUser(cls);
        return multipleResultByQuery(query, user);
    }

    @Override
    public boolean noAdminExists() {
        return (Boolean) requiredSingleResultByNamedQuery(
                "noAdminExistsNativeSQL");
    }

    @Override
    public UserAdmin getAdminOrNullByUser(User user) {
        return (UserAdmin) singleResultOrNullByNamedQuery(
                "adminOrNullByUserHQL", user);
    }
}
