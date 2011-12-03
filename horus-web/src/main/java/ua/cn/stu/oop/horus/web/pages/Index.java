package ua.cn.stu.oop.horus.web.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.util.WebMessages;

/**
 *
 * @author alex
 */
public class Index extends GenericPage{
    
    @Override
    public String getPageTitle(){
        return WebMessages.getMessage("index", getLocale());
    }
    
    @Inject
    private SecurityService securityService;

    public String getStatus() {
        return securityService.isAuthenticated() ? "Authenticated" : "Not Authenticated";
    }
}
