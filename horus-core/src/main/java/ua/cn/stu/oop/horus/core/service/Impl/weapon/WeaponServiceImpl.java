package ua.cn.stu.oop.horus.core.service.Impl.weapon;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.weapon.WeaponDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.series.Series;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.series.SeriesCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.weapon.WeaponService;

/**
 *
 * @author alex
 */
@Service("weaponService")
public class WeaponServiceImpl
        extends GenericServiceImpl<Weapon, WeaponDaoHibernateImpl>
        implements WeaponService {

    @Autowired
    public WeaponServiceImpl(WeaponDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public Weapon getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }

    @Override
    public Collection<Weapon> getEntitiesBySeries(Series series) {
        return (Collection<Weapon>) SeriesCarrierServiceImpl.getEntitiesBySeriesFromDao(series, dao);
    }
}
