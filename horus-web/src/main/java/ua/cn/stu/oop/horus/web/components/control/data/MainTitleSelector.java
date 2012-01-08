package ua.cn.stu.oop.horus.web.components.control.data;

import java.util.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.text.LocalizedTitleService;
import ua.cn.stu.oop.horus.web.util.LocaleUtil;

/**
 *
 * @author alex
 */
public class MainTitleSelector {
    
    @Inject
    @Autowired
    private LocalizedTitleService localizedTitleService;
    
    @Inject
    private PersistentLocale persistentLocale;
    
    private AvailableLocale locale = LocaleUtil.getLocaleFromPersistent(persistentLocale);
    
    @Property
    private String ltitle;
    
    @Component
    private Form theForm;
    
    @Component(id = "formZone")
    private Zone formZone;   
    
    public List<String> onProvideCompletionsFromLtitle(String string) {    
        return new ArrayList<String>(
                localizedTitleService.getMainTitlesForLocaleStartingWithString(
                    locale, string));
    }
}
