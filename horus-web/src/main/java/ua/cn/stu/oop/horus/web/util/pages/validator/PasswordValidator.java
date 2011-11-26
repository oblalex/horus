package ua.cn.stu.oop.horus.web.util.pages.validator;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.Messages;

/**
 *
 * @author alex
 */
@Repository("PasswordValidator")
public class PasswordValidator extends GenericValidator {

    public static final int PASSWORD_LENGTH_MIN = 5;

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
