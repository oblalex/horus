package ua.cn.stu.oop.horus.core.service.text;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.GenericService;

/**
 *
 * @author alex
 */
public interface LocalizedTitleService
        extends GenericService<LocalizedTitle>,
        TitleLinkCarrierService,
        StringTitleCarrierService {
    
    public Collection<LocalizedTitle> getTitlesWithDefaultGrammarByTitleLinkId(Long id);
    public LocalizedTitle getTitleWithDefaultGrammarByTitleLinkIdAndLocale(Long id, AvailableLocale locale);
    public LocalizedTitle getMainTitleForLocaleByTitleLinkId(AvailableLocale locale, Long id);
}
