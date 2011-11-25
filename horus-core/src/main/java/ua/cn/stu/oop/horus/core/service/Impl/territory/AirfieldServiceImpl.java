package ua.cn.stu.oop.horus.core.service.Impl.territory;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.AirfieldDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.*;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.type.TypeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.AirfieldService;

/**
 *
 * @author alex
 */
@Service("airfieldService")
public class AirfieldServiceImpl
        extends GenericServiceImpl<Airfield, AirfieldDaoHibernateImpl>
        implements AirfieldService {

    @Autowired
    public AirfieldServiceImpl(AirfieldDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public Airfield getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }

    @Override
    public Airfield getEntityOrNullByNode(Node node) {
        return NodeCarrierServiceImpl.getEntityOrNullByNodeFromDao(node, dao);
    }

    @Override
    public Collection<Airfield> getEntitiesByType(EntityType type) {
        return (Collection<Airfield>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }
}
