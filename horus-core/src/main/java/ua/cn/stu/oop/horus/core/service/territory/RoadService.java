package ua.cn.stu.oop.horus.core.service.territory;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.type.TypeCarrierService;
import ua.cn.stu.oop.horus.core.domain.territory.Road;

/**
 *
 * @author alex
 */
public interface RoadService
        extends GenericService<Road>,
        NodeCarrierService<Road>,
        TypeCarrierService<Road> {
    
}
