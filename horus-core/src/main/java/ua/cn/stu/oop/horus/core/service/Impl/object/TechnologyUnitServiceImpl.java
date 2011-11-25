package ua.cn.stu.oop.horus.core.service.Impl.object;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.object.TechnologyUnitDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.object.TechnologyUnit;
import ua.cn.stu.oop.horus.core.domain.series.Series;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.series.SeriesCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.object.TechnologyUnitService;

/**
 *
 * @author alex
 */
@Service("technologyUnitService")
public class TechnologyUnitServiceImpl
        extends GenericServiceImpl<TechnologyUnit, TechnologyUnitDaoHibernateImpl>
        implements TechnologyUnitService {

    @Autowired
    public TechnologyUnitServiceImpl(TechnologyUnitDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TechnologyUnit> getEntitiesBySeries(Series series) {
        return (Collection<TechnologyUnit>) SeriesCarrierServiceImpl.getEntitiesBySeriesFromDao(series, dao);
    }
    
}
