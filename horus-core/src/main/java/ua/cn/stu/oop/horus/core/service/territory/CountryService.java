package ua.cn.stu.oop.horus.core.service.territory;

import ua.cn.stu.oop.horus.core.domain.territory.Country;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;

/**
 *
 * @author alex
 */
public interface CountryService
        extends GenericService<Country>,
        TitleLinkCarrierService<Country> {
}
