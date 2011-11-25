package ua.cn.stu.oop.horus.core.service.territory;

import ua.cn.stu.oop.horus.core.domain.territory.BattlegroundArea;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.type.TypeCarrierService;

/**
 *
 * @author alex
 */
public interface BattlegroundAreaService
        extends GenericService<BattlegroundArea>,
        NodeCarrierService<BattlegroundArea>,
        TypeCarrierService<BattlegroundArea> {
}
