package ua.cn.stu.oop.horus.core.service.impl.territory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.SpacetimeUsageInfoDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.SpacetimeUsageInfo;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.SpacetimeUsageInfoService;

/**
 *
 * @author alex
 */
@Service("spacetimeUsageInfoService")
public class SpacetimeUsageInfoServiceImpl
        extends GenericServiceImpl<SpacetimeUsageInfo, SpacetimeUsageInfoDaoHibernateImpl>
        implements SpacetimeUsageInfoService {

    @Autowired
    public SpacetimeUsageInfoServiceImpl(SpacetimeUsageInfoDaoHibernateImpl dao) {
        super(dao);
    }
}
