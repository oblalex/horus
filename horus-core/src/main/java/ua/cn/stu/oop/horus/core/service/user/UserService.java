package ua.cn.stu.oop.horus.core.service.user;

import java.util.Set;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.domain.user.User;

/**
 *
 * @author alex
 */
public interface UserService
        extends GenericService<User> {

    public boolean isUserBannedNow(User user);
    public boolean isLoginUsed(String login);
    public boolean isEmailUsed(String email);
    public boolean isEmailConfirmed(String login);
    public User getUserOrNullByLogin(String login);
    public User getUserOrNullByEmail(String email);
    public Set<String> getUserRoles(User user);
}
