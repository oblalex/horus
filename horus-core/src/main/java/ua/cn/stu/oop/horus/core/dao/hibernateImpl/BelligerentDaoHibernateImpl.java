package ua.cn.stu.oop.horus.core.dao.hibernateImpl;

import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.BelligerentDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleLinkCarrierQueries;
import ua.cn.stu.oop.horus.core.domain.*;

/**
 *
 * @author alex
 */
@Repository("BelligerentDaoHibernateImpl")
public class BelligerentDaoHibernateImpl
        extends GenericDaoHibernateImpl<Belligerent>
        implements BelligerentDao {

    public BelligerentDaoHibernateImpl() {
        super(Belligerent.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public Belligerent getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (Belligerent) singleResultOrNullByQuery(query, titleLink);
    }


}
