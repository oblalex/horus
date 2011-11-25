package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.territory.LocalityDao;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("LocalityDaoHibernateImpl")
public class LocalityDaoHibernateImpl
        extends GenericDaoHibernateImpl<Locality>
        implements LocalityDao {

    public LocalityDaoHibernateImpl() {
        super(Locality.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public Locality getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (Locality) singleResultOrNullByQuery(query, titleLink);
    }

    @Override
    public Locality getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (Locality) singleResultOrNullByQuery(query, node);
    }

    @Override
    public Collection<Locality> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }
}
