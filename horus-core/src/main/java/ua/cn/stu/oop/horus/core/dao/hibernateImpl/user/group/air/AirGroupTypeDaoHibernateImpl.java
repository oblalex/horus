package ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.ParentCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.user.group.air.AirGroupTypeDao;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupType;

/**
 *
 * @author alex
 */
@Repository("AirGroupTypeDaoHibernateImpl")
public class AirGroupTypeDaoHibernateImpl
        extends GenericDaoHibernateImpl<AirGroupType>
        implements AirGroupTypeDao {

    public AirGroupTypeDaoHibernateImpl() {
        super(AirGroupType.class);
    }

    @Override
    public Collection<AirGroupType> getChildrenOfParent(AirGroupType parent) {
        Class cls = getEntityClass();
        String query = ParentCarrierQueries.
                getChildrenByParent(cls);
        String paramTitle = 
                ParentCarrierQueries.PARAMETER_PARENT;
        return multipleResultByQueryWithNamedParam(query, paramTitle, parent);
    }    
}
