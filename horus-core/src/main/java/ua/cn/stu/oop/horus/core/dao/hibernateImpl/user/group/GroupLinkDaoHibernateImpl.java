package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TypeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.group.GroupLinkDao;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.group.GroupLink;

/**
 *
 * @author alex
 */
@Repository("GroupLinkDaoHibernateImpl")
public class GroupLinkDaoHibernateImpl
        extends GenericDaoHibernateImpl<GroupLink>
        implements GroupLinkDao {

    public GroupLinkDaoHibernateImpl() {
        super(GroupLink.class);
    }

    @Override
    public Collection<GroupLink> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }    
}
