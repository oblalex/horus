package ua.cn.stu.oop.horus.web.components;

import java.util.Locale;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.text.LocalizedTitleService;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;

/**
 *
 * @author alex
 */
@Import(library = {
    "context:js/digitalspaghetti.password.js",
    "context:js/jquery.tie.min.js",
    "context:js/jquery.sb.min.js",
    "context:js/radioCheckbox.js",
    "context:js/text-input.js",
    "context:js/cufon-yui.js",
    "context:js/timerBackward.js"},
stylesheet = {"context:css/common.css",
    "context:css/menu.css",
    "context:css/jquery.sb.css",
    "context:css/radioCheckbox.css",
    "context:css/text-input.css",
    "context:css/btn.css"})
public class Layout {
    
    @Inject
    private PersistentLocale persistentLocale;
    
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    public String getPageFullTitle() {
        
        return pageTitle + " - "
                + ConfigContainer.CONFIG.GENERAL.
                    getProjectNameByLocale(getLocaleFromPersistent(persistentLocale));
    }

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
