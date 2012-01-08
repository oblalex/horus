package ua.cn.stu.oop.horus.core.service.impl.text;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.text.LocalizedTitleDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.text.LocalizedTitleService;

/**
 *
 * @author alex
 */
@Service("localizedTitleService")
public class LocalizedTitleServiceImpl
        extends GenericServiceImpl<LocalizedTitle, LocalizedTitleDaoHibernateImpl>
        implements LocalizedTitleService {

    private static final int MIN_STARTING_WITH_STRING_LENGTH = 3;
    
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

    @Override
    public LocalizedTitle getMainTitleForLocaleByTitleLinkId(AvailableLocale locale, Long id) {
        return dao.getMainTitleForLocaleByTitleLinkId(locale, id);
    }

    @Override
    public Collection<String> getMainTitlesForLocaleStartingWithMinimalLengthString(AvailableLocale locale, String string) {
        if (string.length()<MIN_STARTING_WITH_STRING_LENGTH){
            return Collections.EMPTY_LIST;
        }
        return dao.getMainTitlesForLocaleStartingWithString(locale, string);
    }

    @Override
    public int get_MIN_STARTING_WITH_STRING_LENGTH() {
        return MIN_STARTING_WITH_STRING_LENGTH;
    }    
}
