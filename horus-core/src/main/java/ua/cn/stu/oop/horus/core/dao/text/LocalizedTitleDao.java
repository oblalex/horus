package ua.cn.stu.oop.horus.core.dao.text;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public interface LocalizedTitleDao
        extends GenericDao<LocalizedTitle>,
        TitleLinkCarrierDao<LocalizedTitle>,
        StringTitleCarrierDao<LocalizedTitle> {

    public Collection<LocalizedTitle> getTitlesWithDefaultGrammarByTitleLinkId(Long id);
    public LocalizedTitle getTitleWithDefaultGrammarByTitleLinkIdAndLocale(Long id, AvailableLocale locale);
    public LocalizedTitle getMainTitleForLocaleByTitleLinkId(AvailableLocale locale, Long id);
    public Collection<String> getMainTitlesForLocaleStartingWithString(AvailableLocale locale, String string);
}
