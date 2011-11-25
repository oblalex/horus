package ua.cn.stu.oop.horus.core.service.Impl.type;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.type.TypeCarrier;

/**
 *
 * @author alex
 */
public class TypeCarrierServiceImpl {
    
    public static Collection<? extends TypeCarrier> getEntitiesByTypeFromDao(
            EntityType type,
            TypeCarrierDao<? extends TypeCarrier> dao) {
        return dao.getEntitiesByType(type);
    }
}
