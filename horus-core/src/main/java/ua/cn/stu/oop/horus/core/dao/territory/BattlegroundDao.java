package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.dao.*;
import ua.cn.stu.oop.horus.core.domain.territory.Battleground;

/**
 *
 * @author alex
 */
public interface BattlegroundDao
        extends GenericDao<Battleground>,
        TitleLinkCarrierDao<Battleground>,
        NodeCarrierDao<Battleground> {
}
