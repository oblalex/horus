package ua.cn.stu.oop.horus.web.util.pages.validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.GenericSpringTest;

/**
 *
 * @author alex
 */
public class EmailValidatorTest extends GenericSpringTest {

    @Autowired
    private EmailValidator validator;

    @Test
    public void testValidateAndGetErrorMessageOrNull() {
        for (AvailableLocale locale : AvailableLocale.values()) {
            testValidationForLocale(locale);
        }
    }

    private void testValidationForLocale(AvailableLocale locale) {
        validator.validateAndGetErrorMessageOrNull(locale, "mymail");
        validator.validateAndGetErrorMessageOrNull(locale, "mail.mymail");
        validator.validateAndGetErrorMessageOrNull(locale, "my@mail.ml");
        validator.validateAndGetErrorMessageOrNull(locale, "_my@mail.ml");
    }
}
