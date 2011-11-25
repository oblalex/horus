package ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.gameflow.WarDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.War;

/**
 *
 * @author alex
 */
@Repository("WarDaoHibernateImpl")
public class WarDaoHibernateImpl
        extends GenericDaoHibernateImpl<War>
        implements WarDao {

    public WarDaoHibernateImpl() {
        super(War.class);
    }
}
