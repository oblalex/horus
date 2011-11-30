package ua.cn.stu.oop.horus.web.base;

import java.util.Locale;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public abstract class GenericPage {
    
    @Inject
    private PersistentLocale persistentLocale;
    
    private AvailableLocale locale = getLocaleFromPersistent(persistentLocale);
       
    private AvailableLocale getLocaleFromPersistent(PersistentLocale persistentLocale) {
        if (persistentLocale.isSet() == false) {
            locale = AvailableLocale.getDefault();
            persistentLocale.set(new Locale(locale.name()));
        } else {
            locale = AvailableLocale.valueOf(persistentLocale.get().getLanguage());
        }
        return locale;
    }

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
