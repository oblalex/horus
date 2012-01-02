package ua.cn.stu.oop.horus.web.base;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.LocaleUtil;

/**
 *
 * @author alex
 */
public abstract class GenericPage {
    
    @Inject
    private PersistentLocale persistentLocale;
    
    private AvailableLocale locale = LocaleUtil.getLocaleFromPersistent(persistentLocale);

    public AvailableLocale getLocale() {
        return locale;
    }

    public void setLocale(AvailableLocale locale) {
        this.locale = locale;
    }

    public PersistentLocale getPersistentLocale() {
        return persistentLocale;
    }
    
    public abstract String getPageTitle();
}
