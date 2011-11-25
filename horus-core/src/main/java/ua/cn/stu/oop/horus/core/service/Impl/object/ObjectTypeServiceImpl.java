package ua.cn.stu.oop.horus.core.service.Impl.object;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.object.ObjectTypeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.object.ObjectType;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.object.ObjectTypeService;

/**
 *
 * @author alex
 */
@Service("objectTypeService")
public class ObjectTypeServiceImpl
        extends GenericServiceImpl<ObjectType, ObjectTypeDaoHibernateImpl>
        implements ObjectTypeService {

    @Autowired
    public ObjectTypeServiceImpl(ObjectTypeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<ObjectType> getEntitiesByType(EntityType type) {
        return (Collection<ObjectType>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
