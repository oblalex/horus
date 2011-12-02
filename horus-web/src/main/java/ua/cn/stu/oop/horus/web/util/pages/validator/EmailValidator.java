package ua.cn.stu.oop.horus.web.util.pages.validator;

import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
@Component
public class EmailValidator extends GenericValidator {

    @Inject
    @Autowired
    private UserService userService;

    /**
     * @param locale sets the locale of output error message
     * @param obj [String email] - email to validate
     * @return String with localized error description or null in case of successful validation
     */
    @Override
    public String validateAndGetErrorMessageOrNull(AvailableLocale locale, Object... obj) {
        
        String email = (String) obj[0];
        
        if (email == null) {
            return Messages.getMessage("usr.email.undef", locale);
        }
        
        if (email.matches(RegExpUtil.EMAIL_TAMPLATE)==false) {
            return Messages.getMessage("usr.email.bad.format", locale);
        } 
        
        if (ConfigContainer.CONFIG.USER.oneEmailPerUser) {
            if (userService.isEmailUsed(email)) {
                return Messages.getMessage("usr.email.used", locale);
            }
        }
        
        return null;
    }    
}
