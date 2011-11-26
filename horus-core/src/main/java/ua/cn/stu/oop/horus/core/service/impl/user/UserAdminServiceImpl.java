package ua.cn.stu.oop.horus.core.service.impl.user;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.UserAdminDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.UserAdminService;

/**
 *
 * @author alex
 */
@Service("userAdminService")
public class UserAdminServiceImpl
        extends GenericServiceImpl<UserAdmin, UserAdminDaoHibernateImpl>
        implements UserAdminService {

    @Autowired
    public UserAdminServiceImpl(UserAdminDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<UserAdmin> getEntitiesByUser(User user) {
        return (Collection<UserAdmin>) UserCarrierServiceImpl.getEntitiesByUserFromDao(user, dao);
    }

    @Override
    public boolean noAdminExists(){
        return dao.noAdminExists();
    }
}
