package ua.cn.stu.oop.horus.core.service.Impl.object;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.object.CertainObjectTypeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.object.CertainObjectType;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.object.CertainObjectTypeService;

/**
 *
 * @author alex
 */
@Service("certainObjectTypeService")
public class CertainObjectTypeServiceImpl
        extends GenericServiceImpl<CertainObjectType, CertainObjectTypeDaoHibernateImpl>
        implements CertainObjectTypeService {

    @Autowired
    public CertainObjectTypeServiceImpl(CertainObjectTypeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<CertainObjectType> getEntitiesByType(EntityType type) {
        return (Collection<CertainObjectType>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
