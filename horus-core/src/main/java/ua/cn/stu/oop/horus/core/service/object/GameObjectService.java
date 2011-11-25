package ua.cn.stu.oop.horus.core.service.object;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;
import ua.cn.stu.oop.horus.core.domain.object.GameObject;

/**
 *
 * @author alex
 */
public interface GameObjectService
        extends GenericService<GameObject>,
        TitleLinkCarrierService<GameObject> {
    
}
