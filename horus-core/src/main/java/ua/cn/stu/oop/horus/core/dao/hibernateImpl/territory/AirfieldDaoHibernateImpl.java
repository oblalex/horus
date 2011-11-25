package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.territory.AirfieldDao;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
@Repository("AirfieldDaoHibernateImpl")
public class AirfieldDaoHibernateImpl
        extends GenericDaoHibernateImpl<Airfield>
        implements AirfieldDao {

    public AirfieldDaoHibernateImpl() {
        super(Airfield.class);
    }
    
    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public Airfield getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.getCarriersByTitleLink(cls);
        return (Airfield) singleResultOrNullByQuery(query, titleLink);
    }

    @Override
    public Airfield getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (Airfield) singleResultOrNullByQuery(query, node);
    }

    @Override
    public Collection<Airfield> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }
}
