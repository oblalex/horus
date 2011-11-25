package ua.cn.stu.oop.horus.core.service.territory;

import ua.cn.stu.oop.horus.core.domain.territory.Battleground;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;

/**
 *
 * @author alex
 */
public interface BattlegroundService
        extends GenericService<Battleground>, 
        TitleLinkCarrierService<Battleground>,
        NodeCarrierService<Battleground> {
    
}
