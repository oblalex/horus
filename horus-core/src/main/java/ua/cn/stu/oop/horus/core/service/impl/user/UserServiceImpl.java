package ua.cn.stu.oop.horus.core.service.impl.user;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.UserDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.*;
import ua.cn.stu.oop.horus.core.service.user.pilot.PilotService;

/**
 *
 * @author alex
 */
@Service("userService")
public class UserServiceImpl
        extends GenericServiceImpl<User, UserDaoHibernateImpl>
        implements UserService {

    @Autowired
    UserAdminService adminService;
    @Autowired
    UserBanService banService;
    @Autowired
    PilotService pilotService;

    @Autowired
    public UserServiceImpl(UserDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Set<String> getUserRoles(User user) {
        Set<String> roles = new HashSet<String>();
        
        if (adminService.getEntitiesByUser(user).isEmpty()==false){
            roles.add(UserRoles.ADMIN);
        }        
        
        if (pilotService.getEntitiesByUser(user).isEmpty()==false){
            roles.add(UserRoles.PILOT);
        }
        
        if (isUserBannedNow(user)){
            roles.add(UserRoles.BANNED);
        }
        
        return roles;
    }

    @Override
    public User getUserOrNullByLogin(String login) {
        return dao.getUserOrNullByLogin(login);
    }

    @Override
    public boolean isLoginUsed(String login) {
        return dao.isLoginUsed(login);
    }    

    @Override
    public User getUserOrNullByEmail(String email) {
        return dao.getUserOrNullByEmail(email);
    }
    
    @Override
    public boolean isEmailUsed(String email) {
        return dao.isEmailUsed(email);
    }

    @Override
    public boolean isEmailConfirmed(String login) {
        User user= getUserOrNullByLogin(login);
        return (user!=null && user.isEmailConfirmed());
    }
    
    @Override
    public boolean isUserBannedNow(User user) {
        return banService.isUserBannedNow(user);
    }
}
