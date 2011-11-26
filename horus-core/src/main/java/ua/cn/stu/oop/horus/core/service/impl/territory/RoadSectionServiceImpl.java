package ua.cn.stu.oop.horus.core.service.impl.territory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.RoadSectionDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.Node;
import ua.cn.stu.oop.horus.core.domain.territory.RoadSection;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.RoadSectionService;

/**
 *
 * @author alex
 */
@Service("roadSectionService")
public class RoadSectionServiceImpl
        extends GenericServiceImpl<RoadSection, RoadSectionDaoHibernateImpl>
        implements RoadSectionService {

    @Autowired
    public RoadSectionServiceImpl(RoadSectionDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public RoadSection getEntityOrNullByNode(Node node) {
        return NodeCarrierServiceImpl.getEntityOrNullByNodeFromDao(node, dao);
    }
}
