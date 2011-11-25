package ua.cn.stu.oop.horus.core.service.user.pilot.rank;

import ua.cn.stu.oop.horus.core.service.*;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;
import ua.cn.stu.oop.horus.core.domain.user.pilot.rank.Rank;

/**
 *
 * @author alex
 */
public interface RankService
        extends GenericService<Rank>,
        ParentCarrierService<Rank>,
        TitleLinkCarrierService<Rank> {
}
