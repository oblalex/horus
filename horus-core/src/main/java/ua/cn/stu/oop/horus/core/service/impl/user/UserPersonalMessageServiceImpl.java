package ua.cn.stu.oop.horus.core.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.UserPersonalMessageDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.user.UserPersonalMessage;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.UserPersonalMessageService;

/**
 *
 * @author alex
 */
@Service("userPersonalMessageService")
public class UserPersonalMessageServiceImpl
        extends GenericServiceImpl<UserPersonalMessage, UserPersonalMessageDaoHibernateImpl>
        implements UserPersonalMessageService {

    @Autowired
    public UserPersonalMessageServiceImpl(UserPersonalMessageDaoHibernateImpl dao) {
        super(dao);
    }
}
