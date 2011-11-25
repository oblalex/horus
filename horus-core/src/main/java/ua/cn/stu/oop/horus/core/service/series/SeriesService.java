package ua.cn.stu.oop.horus.core.service.series;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;
import ua.cn.stu.oop.horus.core.domain.series.Series;

/**
 *
 * @author alex
 */
public interface SeriesService
        extends GenericService<Series>,
        TitleLinkCarrierService<Series> {
}
