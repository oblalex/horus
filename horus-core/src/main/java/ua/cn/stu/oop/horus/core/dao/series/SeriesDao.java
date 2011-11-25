package ua.cn.stu.oop.horus.core.dao.series;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.series.Series;

/**
 *
 * @author alex
 */
public interface SeriesDao  
        extends GenericDao<Series>,
        TitleLinkCarrierDao<Series> {
    
}
