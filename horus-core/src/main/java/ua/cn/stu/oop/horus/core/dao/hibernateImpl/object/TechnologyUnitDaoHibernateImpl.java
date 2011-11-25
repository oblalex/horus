package ua.cn.stu.oop.horus.core.dao.hibernateImpl.object;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.SeriesCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.object.TechnologyUnitDao;
import ua.cn.stu.oop.horus.core.domain.object.TechnologyUnit;
import ua.cn.stu.oop.horus.core.domain.series.Series;

/**
 *
 * @author alex
 */
@Repository("TechnologyUnitDaoHibernateImpl")
public class TechnologyUnitDaoHibernateImpl
        extends GenericDaoHibernateImpl<TechnologyUnit>
        implements TechnologyUnitDao {
    
    public TechnologyUnitDaoHibernateImpl() {
        super(TechnologyUnit.class);
    }

    @Override
    public Collection<TechnologyUnit> getEntitiesBySeries(Series series) {
        Class cls = getEntityClass();
        String query = SeriesCarrierQueries.
                getCarrierBySeries(cls);
        return multipleResultByQuery(query, series);
    }
}
