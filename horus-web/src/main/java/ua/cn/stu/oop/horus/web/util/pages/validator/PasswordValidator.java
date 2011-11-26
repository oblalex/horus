package ua.cn.stu.oop.horus.web.util.pages.validator;

import org.springframework.stereotype.Component;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.Messages;

/**
 *
 * @author alex
 */
@Component
public class PasswordValidator extends GenericValidator {

    public static final int PASSWORD_LENGTH_MIN = 5;

    /**
     * @param locale sets the locale of output error message
     * @param obj [String password, String passwordConfirm] - password with passwordConfirm to validate
     * @return String with localized error description or null in case of successful validation
     */
    @Override
    public String validateAndGetErrorMessageOrNull(AvailableLocale locale, Object... obj) {

        String password = (String) obj[0];
        String passwordConfirm = (String) obj[1];

        if (password == null) {
            return Messages.getMessage("usr.pswd.undef", locale);
        }

        if (password.length() < PASSWORD_LENGTH_MIN) {
            return Messages.getMessage("usr.pswd.too.short", locale);
        }

        if ((passwordConfirm == null)
                || password.equals(passwordConfirm) == false) {
            return Messages.getMessage("usr.pswd.do.not.match", locale);
        }

        return null;
    }
}
