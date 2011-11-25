package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupDao;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupLink;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;

/**
 *
 * @author alex
 */
@Repository("AirGroupDaoHibernateImpl")
public class AirGroupDaoHibernateImpl
        extends GenericDaoHibernateImpl<AirGroup>
        implements AirGroupDao {

    public AirGroupDaoHibernateImpl() {
        super(AirGroup.class);
    }

    @Override
    public Collection<AirGroup> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }

    @Override
    public Collection<String> getAllTitles() {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getAllTitles(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public AirGroup getEntityOrNullByTitle(String title) {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getTitleCarriersByTitle(cls);
        return (AirGroup) singleResultOrNullByQuery(query, title);
    }

    @Override
    public Collection<AirGroup> getEntitiesByGroupLink(GroupLink groupLink) {
        Class cls = getEntityClass();
        String query = GroupLinkCarrierQueries.
                getCarriersByGroupLink(cls);
        return multipleResultByQuery(query, groupLink);
    }    
}
