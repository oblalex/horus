package ua.cn.stu.oop.horus.core.service.impl.user.group.air;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air.AirForceDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirForce;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirForceService;

/**
 *
 * @author alex
 */
@Service("airForceService")
public class AirForceServiceImpl
        extends GenericServiceImpl<AirForce, AirForceDaoHibernateImpl>
        implements AirForceService {

    @Autowired
    public AirForceServiceImpl(AirForceDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public AirForce getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }
}
