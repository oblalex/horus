package ua.cn.stu.oop.horus.core.service.impl.user;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.user.UserCarrierDao;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.UserCarrier;

/**
 *
 * @author alex
 */
public class UserCarrierServiceImpl {
    
    public static Collection<? extends UserCarrier> getEntitiesByUserFromDao(
            User user,
            UserCarrierDao<? extends UserCarrier> dao) {
        return dao.getEntitiesByUser(user);
    }
    
    public static Collection<? extends UserCarrier> getAllSortedByUserLogin(
            UserCarrierDao<? extends UserCarrier> dao) {
        return dao.getAllSortedByUserLogin();
    }
}
