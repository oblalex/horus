package ua.cn.stu.oop.horus.web.components;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;

/**
 *
 * @author alex
 */
@Import(library = "context:js/logoLoader.js")
public class Header {

    @Inject
    private PersistentLocale persistentLocale;
    private AvailableLocale locale = Layout.getLocaleFromPersistent(persistentLocale);
    
    public String getProjectName() {
        return ConfigContainer.CONFIG.GENERAL.getProjectNameByLocale(locale);
    }
    
    public String getProjectDescription() {
        return ConfigContainer.CONFIG.GENERAL.getProjectDecriptionByLocale(locale);
    }
}
