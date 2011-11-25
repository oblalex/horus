package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleLinkCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirForceDao;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirForce;

/**
 *
 * @author alex
 */
@Repository("AirForceDaoHibernateImpl")
public class AirForceDaoHibernateImpl
        extends GenericDaoHibernateImpl<AirForce>
        implements AirForceDao {

    public AirForceDaoHibernateImpl() {
        super(AirForce.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
		Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public AirForce getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (AirForce) singleResultOrNullByQuery(query, titleLink);
    }    
}
