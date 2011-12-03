package ua.cn.stu.oop.horus.web.util;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Locale;
import java.util.ResourceBundle;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public class WebMessages {

    private static final String BUNDLE_NAME = "i18n/app";
    private static final EnumMap<AvailableLocale, ResourceBundle> bundles =
            new EnumMap<AvailableLocale, ResourceBundle>(AvailableLocale.class);

    static {
        initBundles();
    }

    private static void initBundles() {
        for (AvailableLocale al : AvailableLocale.values()) {
            addBundle(al);
        }
    }

    private static void addBundle(AvailableLocale avaivableLocale) {
        Locale l = new Locale(avaivableLocale.name());
        ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME, l);
        bundles.put(avaivableLocale, rb);
    }

    public static String getMessage(String key, AvailableLocale locale) {
        return bundles.get(locale).getString(key);
    }

    public static String getMessageParameterized(String key, AvailableLocale locale, String... params) {        
        return new MessageFormat(bundles.get(locale).getString(key)).format(params);
    }
}
