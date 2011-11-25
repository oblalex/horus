package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleLinkCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupSpecializationDao;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupSpecialization;

/**
 *
 * @author alex
 */
@Repository("AirGroupSpecializationDaoHibernateImpl")
public class AirGroupSpecializationDaoHibernateImpl
        extends GenericDaoHibernateImpl<AirGroupSpecialization>
        implements AirGroupSpecializationDao {

    public AirGroupSpecializationDaoHibernateImpl() {
        super(AirGroupSpecialization.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public AirGroupSpecialization getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (AirGroupSpecialization) singleResultOrNullByQuery(query, titleLink);
    }    
}
