package ua.cn.stu.oop.horus.core.service.territory;

import ua.cn.stu.oop.horus.core.domain.territory.BattlegroundPoint;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.type.TypeCarrierService;

/**
 *
 * @author alex
 */
public interface BattlegroundPointService
        extends GenericService<BattlegroundPoint>,
        NodeCarrierService<BattlegroundPoint>,
        TypeCarrierService<BattlegroundPoint> {
}
