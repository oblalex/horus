package ua.cn.stu.oop.horus.core.dao.series;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.series.*;

/**
 *
 * @author alex
 */
public interface SeriesCarrierDao<E extends SeriesCarrier> {
    
    public Collection<E> getEntitiesBySeries(Series series);
}
