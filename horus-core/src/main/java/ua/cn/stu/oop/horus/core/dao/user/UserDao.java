package ua.cn.stu.oop.horus.core.dao.user;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.user.User;

/**
 *
 * @author alex
 */
public interface UserDao
        extends GenericDao<User> {

    public Boolean isLoginUsed(String login);
    public Boolean isEmailUsed(String email);
    public Boolean isEmailConfirmed(String login);
    public User getUserOrNullByEmail(String email);
    public User getUserOrNullByLogin(String login);
}
