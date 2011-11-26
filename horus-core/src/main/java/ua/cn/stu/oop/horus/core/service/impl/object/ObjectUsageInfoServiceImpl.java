package ua.cn.stu.oop.horus.core.service.impl.object;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.object.ObjectUsageInfoDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.object.ObjectUsageInfo;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.object.ObjectUsageInfoService;

/**
 *
 * @author alex
 */
@Service("objectUsageInfoService")
public class ObjectUsageInfoServiceImpl
        extends GenericServiceImpl<ObjectUsageInfo, ObjectUsageInfoDaoHibernateImpl>
        implements ObjectUsageInfoService {

    @Autowired
    public ObjectUsageInfoServiceImpl(ObjectUsageInfoDaoHibernateImpl dao) {
        super(dao);
    }
}
