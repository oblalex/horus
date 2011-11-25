package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.user.UserPersonalMessageDao;
import ua.cn.stu.oop.horus.core.domain.user.UserPersonalMessage;

/**
 *
 * @author alex
 */
@Repository("UserPersonalMessageDaoHibernateImpl")
public class UserPersonalMessageDaoHibernateImpl
        extends GenericDaoHibernateImpl<UserPersonalMessage>
        implements UserPersonalMessageDao {

    public UserPersonalMessageDaoHibernateImpl() {
        super(UserPersonalMessage.class);
    }    
}
