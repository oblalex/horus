package ua.cn.stu.oop.horus.web.services;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.AccountException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.*;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.service.user.UserService;

/**
 *
 * @author alex
 */
public class UserRealm extends AuthorizingRealm {

    UserService userService;

    @Autowired
    public UserRealm(UserService userService) {
        super(new MemoryConstrainedCacheManager());
        this.userService = userService;
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        setCredentialsMatcher(new HashedCredentialsMatcher(Sha1Hash.ALGORITHM_NAME));
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection was null, which should not happen");
        }

        if (principals.isEmpty()) {
            return null;
        }

        if (principals.fromRealm(getName()).size() <= 0) {
            return null;
        }

        String login = (String) principals.fromRealm(getName()).iterator().next();
        if (login == null) {
            return null;
        }

        User user = userService.getUserOrNullByLogin(login);
        if (user == null) {
            return null;
        }
        Set<String> roles = userService.getUserRoles(user);
        return new SimpleAuthorizationInfo(roles);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        String login = upToken.getUsername();

        if (login == null) {
            try {
                throw new AccountException("Null logins are not allowed by this realm.");
            } catch (AccountException ex) {
                // TODO: logger UserRealm
                Logger.getLogger(UserRealm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        User user = userService.getUserOrNullByLogin(login);

        if (user == null) {
            throw new UnknownAccountException("User was not found.");
        }       

        if (userService.isUserBannedNow(user)) {
            throw new LockedAccountException("Account [" + login + "] is locked.");
        }

        return new SimpleAuthenticationInfo(login, user.getHashedPassword(), new SimpleByteSource(user.getSalt()), getName());
    }
}
