package ua.cn.stu.oop.horus.core.service.series;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.series.*;

/**
 *
 * @author alex
 */
public interface SeriesCarrierService<E extends SeriesCarrier> {
    
    public Collection<E> getEntitiesBySeries(Series series);
}
