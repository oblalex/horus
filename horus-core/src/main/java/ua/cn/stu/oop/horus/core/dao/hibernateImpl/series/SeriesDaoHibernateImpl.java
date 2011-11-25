package ua.cn.stu.oop.horus.core.dao.hibernateImpl.series;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleLinkCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.series.SeriesDao;
import ua.cn.stu.oop.horus.core.domain.series.Series;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;

/**
 *
 * @author alex
 */
@Repository("SeriesDaoHibernateImpl")
public class SeriesDaoHibernateImpl 
        extends GenericDaoHibernateImpl<Series>
        implements SeriesDao {
    
     public SeriesDaoHibernateImpl() {
        super(Series.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public Series getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (Series) singleResultOrNullByQuery(query, titleLink);
    }
}
