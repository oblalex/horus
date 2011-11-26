package ua.cn.stu.oop.horus.core.service.impl.user.group.air;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.user.group.air.AirGroupSpecializationDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupSpecialization;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.user.group.air.AirGroupSpecializationService;

/**
 *
 * @author alex
 */
@Service("airGroupSpecializationService")
public class AirGroupSpecializationServiceImpl
        extends GenericServiceImpl<AirGroupSpecialization, AirGroupSpecializationDaoHibernateImpl>
        implements AirGroupSpecializationService {

    @Autowired
    public AirGroupSpecializationServiceImpl(AirGroupSpecializationDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public AirGroupSpecialization getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }
}
