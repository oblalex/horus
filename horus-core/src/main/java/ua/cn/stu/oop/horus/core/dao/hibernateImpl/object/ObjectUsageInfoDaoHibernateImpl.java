package ua.cn.stu.oop.horus.core.dao.hibernateImpl.object;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.object.ObjectUsageInfoDao;
import ua.cn.stu.oop.horus.core.domain.object.ObjectUsageInfo;

/**
 *
 * @author alex
 */
@Repository("ObjectUsageInfoDaoHibernateImpl")
public class ObjectUsageInfoDaoHibernateImpl 
        extends GenericDaoHibernateImpl<ObjectUsageInfo>
        implements ObjectUsageInfoDao {

    public ObjectUsageInfoDaoHibernateImpl() {
        super(ObjectUsageInfo.class);
    } 
}
