package ua.cn.stu.oop.horus.web.util.pages.validator;

import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
@Component
public class LoginValidator extends GenericValidator {

    @Inject
    @Autowired
    private UserService userService;

    /**
     * @param locale sets the locale of output error message
     * @param obj [String login] - login to validate
     * @return String with localized error description or null in case of successful validation
     */
    @Override
    public String validateAndGetErrorMessageOrNull(AvailableLocale locale, Object... obj) {
        
        String login = (String) obj[0];

        if (login == null) {
            return Messages.getMessage("usr.login.undef", locale);
        }

        if (login.matches(RegExpUtil.LOGIN_TAMPLATE) == false) {
            return Messages.getMessage("usr.login.bad.format", locale);
        }

        if (userService.isLoginUsed(login)) {
            return Messages.getMessage("usr.exists", locale);
        }
        
        return null;
    }    
}
