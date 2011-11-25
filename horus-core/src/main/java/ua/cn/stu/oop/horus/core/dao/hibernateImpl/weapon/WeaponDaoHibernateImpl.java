package ua.cn.stu.oop.horus.core.dao.hibernateImpl.weapon;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.weapon.WeaponDao;
import ua.cn.stu.oop.horus.core.domain.series.Series;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;

/**
 *
 * @author alex
 */
@Repository("WeaponDaoHibernateImpl")
public class WeaponDaoHibernateImpl
        extends GenericDaoHibernateImpl<Weapon>
        implements WeaponDao {

    public WeaponDaoHibernateImpl() {
        super(Weapon.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public Weapon getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (Weapon) singleResultOrNullByQuery(query, titleLink);
    }

    @Override
    public Collection<Weapon> getEntitiesBySeries(Series series) {
        Class cls = getEntityClass();
        String query = SeriesCarrierQueries.
                getCarrierBySeries(cls);
        return multipleResultByQuery(query, series);
    }    
}
