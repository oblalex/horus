package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.Country;

/**
 *
 * @author alex
 */
public interface CountryDao
        extends GenericDao<Country>,
        TitleLinkCarrierDao<Country> {
    
}
