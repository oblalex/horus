package ua.cn.stu.oop.horus.core.service.territory;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;
import ua.cn.stu.oop.horus.core.domain.territory.TheatreOfWar;

/**
 *
 * @author alex
 */
public interface TheatreOfWarService
        extends GenericService<TheatreOfWar>,
        TitleLinkCarrierService<TheatreOfWar>,
        NodeCarrierService<TheatreOfWar> {
    
}
