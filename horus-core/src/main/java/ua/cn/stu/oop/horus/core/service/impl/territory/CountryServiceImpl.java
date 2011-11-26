package ua.cn.stu.oop.horus.core.service.impl.territory;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory.CountryDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.territory.Country;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.territory.CountryService;

/**
 *
 * @author alex
 */
@Service("countryService")
public class CountryServiceImpl
        extends GenericServiceImpl<Country, CountryDaoHibernateImpl>
        implements CountryService {

    @Autowired
    public CountryServiceImpl(CountryDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public Country getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }
    
}
