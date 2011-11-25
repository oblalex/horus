package ua.cn.stu.oop.horus.web.components;

import java.util.Locale;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.pages.Index;
import ua.cn.stu.oop.horus.web.util.Messages;

/**
 *
 * @author alex
 */
public class Roof {

    
    @Inject
    @Autowired
    private UserService userService;
    @Inject
    private PersistentLocale persistentLocale;
    private AvailableLocale locale = Layout.getLocaleFromPersistent(persistentLocale);
    @Inject
    private SecurityService securityService;

    void onActionFromEnLocaleLink() {
        locale = AvailableLocale.en;
        updateLocale();
    }

    void onActionFromRuLocaleLink() {
        locale = AvailableLocale.ru;
        updateLocale();
    }
    
    private void updateLocale(){
        persistentLocale.set(new Locale(locale.name()));
        
        Subject currentUser = securityService.getSubject();

        if (currentUser == null) {
            throw new IllegalStateException("Subject can`t be null");
        }
        
        String principal = (String) currentUser.getPrincipal();
        
        if (principal != null) {
            User user = userService.getUserOrNullByLogin(principal);
            user.setPreferredLocale(locale);
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
        return Messages.getMessage("registration", locale);
    }

    public String getTitleLanguage() {
        return Messages.getMessage("lang", locale);
    }
}
