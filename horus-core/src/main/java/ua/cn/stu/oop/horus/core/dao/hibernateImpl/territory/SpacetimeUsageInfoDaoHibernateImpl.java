package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.territory.SpacetimeUsageInfoDao;
import ua.cn.stu.oop.horus.core.domain.territory.SpacetimeUsageInfo;

/**
 *
 * @author alex
 */
@Repository("SpacetimeUsageInfoDaoHibernateImpl")
public class SpacetimeUsageInfoDaoHibernateImpl
        extends GenericDaoHibernateImpl<SpacetimeUsageInfo>
        implements SpacetimeUsageInfoDao {
    
    public SpacetimeUsageInfoDaoHibernateImpl() {
        super(SpacetimeUsageInfo.class);
    }
}
