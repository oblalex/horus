package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleLinkCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupStatusInAirForceDao;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupStatusInAirForce;

/**
 *
 * @author alex
 */
@Repository("AirGroupStatusInAirForceDaoHibernateImpl")
public class AirGroupStatusInAirForceDaoHibernateImpl
        extends GenericDaoHibernateImpl<AirGroupStatusInAirForce>
        implements AirGroupStatusInAirForceDao {

    public AirGroupStatusInAirForceDaoHibernateImpl() {
        super(AirGroupStatusInAirForce.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public AirGroupStatusInAirForce getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (AirGroupStatusInAirForce)
                singleResultOrNullByQuery(query, titleLink);
    }    
}
