package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.user.UserDao;
import ua.cn.stu.oop.horus.core.domain.user.User;

/**
 *
 * @author alex
 */
@Repository("UserDaoHibernateImpl")
public class UserDaoHibernateImpl
        extends GenericDaoHibernateImpl<User>
        implements UserDao {

    public UserDaoHibernateImpl() {
        super(User.class);
    }

    @Override
    public User getUserOrNullByLogin(String login) {
        return (User) singleResultOrNullByNamedQuery(
                "findUserOrNullByLoginNativeSQL",
                login);
    }

    @Override
    public User getUserOrNullByEmail(String email) {
        return (User) singleResultOrNullByNamedQuery(
                "findUserOrNullByEmailNativeSQL",
                email);
    }

    @Override
    public Boolean isLoginUsed(String login) {
        return (Boolean) requiredSingleResultByNamedQuery(
                "isLoginUsedNativeSQL",
                login);
    }

    @Override
    public Boolean isEmailUsed(String email) {
        return (Boolean) requiredSingleResultByNamedQuery(
                "isEmailUsedNativeSQL",
                email);
    }

    @Override
    public Boolean isEmailConfirmed(String login) {
        return (Boolean) requiredSingleResultByNamedQuery(
                "isEmailConfirmedNativeSQL",
                login);
    }
}
