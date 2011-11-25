package ua.cn.stu.oop.horus.web.util.pages.validator;

import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/webContext.xml"})
public class LoginValidatorTest extends AbstractTransactionalJUnit4SpringContextTests {
    
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
