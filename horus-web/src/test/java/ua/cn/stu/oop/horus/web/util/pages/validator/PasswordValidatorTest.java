package ua.cn.stu.oop.horus.web.util.pages.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/webContext.xml"})
public class PasswordValidatorTest extends AbstractTransactionalJUnit4SpringContextTests {

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
