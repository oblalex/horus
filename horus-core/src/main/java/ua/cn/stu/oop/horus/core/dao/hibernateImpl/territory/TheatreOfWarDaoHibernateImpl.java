package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.territory.TheatreOfWarDao;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;

/**
 *
 * @author alex
 */
@Repository("TheatreOfWarDaoHibernateImpl")
public class TheatreOfWarDaoHibernateImpl
        extends GenericDaoHibernateImpl<TheatreOfWar>
        implements TheatreOfWarDao {

    public TheatreOfWarDaoHibernateImpl() {
        super(TheatreOfWar.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public TheatreOfWar getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (TheatreOfWar) singleResultOrNullByQuery(query, titleLink);
    }

    @Override
    public TheatreOfWar getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (TheatreOfWar) singleResultOrNullByQuery(query, node);
    }    
}
