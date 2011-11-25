package ua.cn.stu.oop.horus.core.service.Impl.territory;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.TheatreOfWarDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.TheatreOfWarService;

/**
 *
 * @author alex
 */
@Service("theatreOfWarService")
public class TheatreOfWarServiceImpl
        extends GenericServiceImpl<TheatreOfWar, TheatreOfWarDaoHibernateImpl>
        implements TheatreOfWarService {

    @Autowired
    public TheatreOfWarServiceImpl(TheatreOfWarDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public TheatreOfWar getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }

    @Override
    public TheatreOfWar getEntityOrNullByNode(Node node) {
        return NodeCarrierServiceImpl.getEntityOrNullByNodeFromDao(node, dao);
    }
    
}
