package ua.cn.stu.oop.horus.web.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.components.Layout;
import ua.cn.stu.oop.horus.web.util.Messages;

/**
 *
 * @author alex
 */
public class Index {

    @Inject
    private PersistentLocale persistentLocale;
    private AvailableLocale locale = Layout.getLocaleFromPersistent(persistentLocale);
    
    public String getPageTitle(){
        return Messages.getMessage("index", locale);
    }
    
    @Inject
    private SecurityService securityService;

    public String getStatus() {
        return securityService.isAuthenticated() ? "Authenticated" : "Not Authenticated";
    }
}
