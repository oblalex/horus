package ua.cn.stu.oop.horus.core.service.impl.series;

import java.util.Collection;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.series.SeriesCarrierDao;
import ua.cn.stu.oop.horus.core.domain.series.*;

/**
 *
 * @author alex
 */
@Service("seriesCarrierService")
public class SeriesCarrierServiceImpl {
    
    public static Collection<? extends SeriesCarrier> getEntitiesBySeriesFromDao(
            Series series,
            SeriesCarrierDao<? extends SeriesCarrier> dao) {
        return dao.getEntitiesBySeries(series);
    }
}
