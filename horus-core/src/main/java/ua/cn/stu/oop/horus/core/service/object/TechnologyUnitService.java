package ua.cn.stu.oop.horus.core.service.object;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.series.SeriesCarrierService;
import ua.cn.stu.oop.horus.core.domain.object.TechnologyUnit;

/**
 *
 * @author alex
 */
public interface TechnologyUnitService
        extends GenericService<TechnologyUnit>,
        SeriesCarrierService<TechnologyUnit> {
}
