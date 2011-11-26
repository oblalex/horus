package ua.cn.stu.oop.horus.core.service.impl.user.pilot.rank;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.ParentCarrierDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.pilot.rank.RankDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.user.pilot.rank.Rank;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.ParentCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.pilot.rank.RankService;

/**
 *
 * @author alex
 */
@Service("rankService")
public class RankServiceImpl
        extends GenericServiceImpl<Rank, RankDaoHibernateImpl>
        implements RankService {

    @Autowired
    public RankServiceImpl(RankDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<Rank> getChildrenOfParent(Rank parent) {
        return (Collection<Rank>) ParentCarrierServiceImpl.getChildrenOfParentFromDao(parent, (ParentCarrierDao)dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public Rank getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }
}
