package ua.cn.stu.oop.horus.core.service.impl.user.group.air;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air.AirGroupStatusInAirForceDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupStatusInAirForce;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirGroupStatusInAirForceService;

/**
 *
 * @author alex
 */
@Service("airGroupStatusInAirForceService")
public class AirGroupStatusInAirForceServiceImpl
        extends GenericServiceImpl<AirGroupStatusInAirForce, AirGroupStatusInAirForceDaoHibernateImpl>
        implements AirGroupStatusInAirForceService {

    @Autowired
    public AirGroupStatusInAirForceServiceImpl(AirGroupStatusInAirForceDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public AirGroupStatusInAirForce getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }
}
