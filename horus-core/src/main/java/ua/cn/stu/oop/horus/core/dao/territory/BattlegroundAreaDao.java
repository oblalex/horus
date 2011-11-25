package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.BattlegroundArea;

/**
 *
 * @author alex
 */
public interface BattlegroundAreaDao
        extends GenericDao<BattlegroundArea>,
        NodeCarrierDao<BattlegroundArea>,
        TypeCarrierDao<BattlegroundArea> {
    
}
