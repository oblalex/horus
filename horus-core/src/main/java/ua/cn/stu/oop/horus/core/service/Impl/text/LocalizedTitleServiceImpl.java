package ua.cn.stu.oop.horus.core.service.Impl.text;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.text.LocalizedTitleDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.text.LocalizedTitleService;

/**
 *
 * @author alex
 */
@Service("localizedTitleService")
public class LocalizedTitleServiceImpl
        extends GenericServiceImpl<LocalizedTitle, LocalizedTitleDaoHibernateImpl>
        implements LocalizedTitleService {

    @Autowired
    public LocalizedTitleServiceImpl(LocalizedTitleDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public TitleLinkCarrier getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }

    @Override
    public Collection getAllTitles() {
        return StringTitleCarrierServiceImpl.getAllTitlesFromDao(dao);
    }

    @Override
    public StringTitleCarrier getEntityOrNullByTitle(String title) {
        return StringTitleCarrierServiceImpl.getEntityOrNullByTitleFromDao(title, dao);
    }

    @Override
    public Collection<LocalizedTitle> getTitlesWithDefaultGrammarByTitleLinkId(Long id) {
        return dao.getTitlesWithDefaultGrammarByTitleLinkId(id);
    }

    @Override
    public LocalizedTitle getTitleWithDefaultGrammarByTitleLinkIdAndLocale(Long id, AvailableLocale locale) {
        return dao.getTitleWithDefaultGrammarByTitleLinkIdAndLocale(id, locale);
    }
}
