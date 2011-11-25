package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.pilot.rank;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.user.pilot.rank.RankDao;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.user.pilot.rank.Rank;

/**
 *
 * @author alex
 */
@Repository("RankDaoHibernateImpl")
public class RankDaoHibernateImpl
        extends GenericDaoHibernateImpl<Rank>
        implements RankDao {

    public RankDaoHibernateImpl() {
        super(Rank.class);
    }

    @Override
    public Collection<Rank> getChildrenOfParent(Rank parent) {
        Class cls = getEntityClass();
        String query = ParentCarrierQueries.
                getChildrenByParent(cls);
        String paramTitle =
                ParentCarrierQueries.PARAMETER_PARENT;
        return multipleResultByQueryWithNamedParam(query, paramTitle, parent);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {        
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public Rank getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (Rank) singleResultOrNullByQuery(query, titleLink);
    }    
}
