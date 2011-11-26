package ua.cn.stu.oop.horus.web.util.pages.validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.GenericSpringTest;

/**
 *
 * @author alex
 */
public class PasswordValidatorTest extends GenericSpringTest {

    @Autowired
    private PasswordValidator validator;

    @Test
    public void testValidateAndGetErrorMessageOrNull() {
        for (AvailableLocale locale: AvailableLocale.values()) {
            testValidationForLocale(locale);
        }
    }
    
    private void testValidationForLocale(AvailableLocale locale){
        validator.validateAndGetErrorMessageOrNull(locale, "123", "123");
        validator.validateAndGetErrorMessageOrNull(locale, "12345", "12345");
        validator.validateAndGetErrorMessageOrNull(locale, "12345", "54321");
    }
}
