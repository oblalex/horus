package ua.cn.stu.oop.horus.web.components;

import java.util.Locale;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.pages.Index;
import ua.cn.stu.oop.horus.web.util.WebMessages;

/**
 *
 * @author alex
 */
public class Roof extends GenericPage{

    
    @Inject
    @Autowired
    private UserService userService;
    
    @Inject
    private SecurityService securityService;

    void onActionFromEnLocaleLink() {
        setLocale(AvailableLocale.en);
        updateLocale();
    }

    void onActionFromRuLocaleLink() {
        setLocale(AvailableLocale.ru);
        updateLocale();
    }
    
    private void updateLocale(){
        
        AvailableLocale aLoc = getLocale();
        
        getPersistentLocale().set(new Locale(aLoc.name()));
        
        Subject currentUser = securityService.getSubject();

        if (currentUser == null) {
            throw new IllegalStateException("Subject can`t be null");
        }
        
        String principal = (String) currentUser.getPrincipal();
        
        if (principal != null) {
            User user = userService.getUserOrNullByLogin(principal);
            user.setPreferredLocale(aLoc);
            userService.saveOrUpdateEntity(user);
        }
    }

    Object onActionFromLogOut() {
        Subject currentUser = securityService.getSubject();

        if (currentUser == null) {
            throw new IllegalStateException("Subject can`t be null");
        }

        currentUser.logout();

        return Index.class;
    }

    public String getTitleRegistration() {
        return WebMessages.getMessage("registration", getLocale());
    }

    public String getTitleLanguage() {
        return WebMessages.getMessage("lang", getLocale());
    }

    @Override
    public String getPageTitle() {
        return this.getClass().getSimpleName();
    }
}
