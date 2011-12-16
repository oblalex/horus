package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.user.group.GroupEnterRequestDao;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.group.*;

/**
 *
 * @author alex
 */
@Repository("GroupEnterRequestDaoHibernateImpl")
public class GroupEnterRequestDaoHibernateImpl
        extends GenericDaoHibernateImpl<GroupEnterRequest>
        implements GroupEnterRequestDao {

    public GroupEnterRequestDaoHibernateImpl() {
        super(GroupEnterRequest.class);
    }

    @Override
    public Collection<GroupEnterRequest> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }

    @Override
    public Collection<GroupEnterRequest> getEntitiesByUser(User user) {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getCarriersByUser(cls);
        return multipleResultByQuery(query, user);
    }

    @Override
    public Collection<GroupEnterRequest> getEntitiesByGroupLink(GroupLink groupLink) {
        Class cls = getEntityClass();
        String query = GroupLinkCarrierQueries.
                getCarriersByGroupLink(cls);
        return multipleResultByQuery(query, groupLink);
    }

    @Override
    public Collection<GroupEnterRequest> getAllSortedByUserLogin() {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getAllSortedByUserLogin(cls);
        return multipleResultByQuery(query);
    }
}
