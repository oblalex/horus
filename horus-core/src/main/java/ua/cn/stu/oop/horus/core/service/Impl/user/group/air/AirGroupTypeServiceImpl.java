package ua.cn.stu.oop.horus.core.service.Impl.user.group.air;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.ParentCarrierDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air.AirGroupTypeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupType;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.ParentCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirGroupTypeService;

/**
 *
 * @author alex
 */
@Service("airGroupTypeService")
public class AirGroupTypeServiceImpl
        extends GenericServiceImpl<AirGroupType, AirGroupTypeDaoHibernateImpl>
        implements AirGroupTypeService {

    @Autowired
    public AirGroupTypeServiceImpl(AirGroupTypeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<AirGroupType> getChildrenOfParent(AirGroupType parent) {
        return (Collection<AirGroupType>) ParentCarrierServiceImpl.getChildrenOfParentFromDao(parent, (ParentCarrierDao)dao);
    }
}
