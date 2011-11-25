package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.BattlegroundPoint;

/**
 *
 * @author alex
 */
public interface BattlegroundPointDao 
        extends GenericDao<BattlegroundPoint>,
        NodeCarrierDao<BattlegroundPoint>,
        TypeCarrierDao<BattlegroundPoint> {
    
}
