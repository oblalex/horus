package ua.cn.stu.oop.horus.web.util;

import java.util.Locale;
import org.apache.tapestry5.services.PersistentLocale;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public class LocaleUtil {
    
    public static AvailableLocale getLocaleFromPersistent(PersistentLocale persistentLocale) {
        AvailableLocale locale;
        if (persistentLocale.isSet() == false) {
            locale = AvailableLocale.getDefault();
            persistentLocale.set(new Locale(locale.name()));
        } else {
            locale = AvailableLocale.valueOf(persistentLocale.get().getLanguage());
        }
        return locale;
    }
}
