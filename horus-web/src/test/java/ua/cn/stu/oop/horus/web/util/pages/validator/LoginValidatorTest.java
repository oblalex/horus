package ua.cn.stu.oop.horus.web.util.pages.validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.GenericSpringTest;

/**
 *
 * @author alex
 */
public class LoginValidatorTest extends GenericSpringTest {
    
    @Autowired
    private LoginValidator validator;
    
    @Test
    public void testValidateAndGetErrorMessageOrNull() {
        for (AvailableLocale locale: AvailableLocale.values()) {
            testValidationForLocale(locale);
        }
    }
    
    private void testValidationForLocale(AvailableLocale locale){
        validator.validateAndGetErrorMessageOrNull(locale, "admin");
        validator.validateAndGetErrorMessageOrNull(locale, "!root");
        validator.validateAndGetErrorMessageOrNull(locale, "<div>");
        validator.validateAndGetErrorMessageOrNull(locale, "фыва");
        validator.validateAndGetErrorMessageOrNull(locale, "&^{}");
    }
}
