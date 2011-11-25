package ua.cn.stu.oop.horus.core.dao.user.pilot.rank;

import ua.cn.stu.oop.horus.core.dao.*;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.user.pilot.rank.Rank;

/**
 *
 * @author alex
 */
public interface RankDao
        extends GenericDao<Rank>,
        ParentCarrierDao<Rank>,
        TitleLinkCarrierDao<Rank> {
}
