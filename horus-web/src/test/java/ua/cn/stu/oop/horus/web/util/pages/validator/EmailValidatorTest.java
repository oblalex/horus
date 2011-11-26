package ua.cn.stu.oop.horus.web.util.pages.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/webContext.xml"})
public class EmailValidatorTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    @Autowired
    private EmailValidator validator;        

    @Test
    public void testValidateAndGetErrorMessageOrNull() {
        for (AvailableLocale locale: AvailableLocale.values()) {
            testValidationForLocale(locale);
        }
    }
    
    private void testValidationForLocale(AvailableLocale locale){
        validator.validateAndGetErrorMessageOrNull(locale, "mymail");
        validator.validateAndGetErrorMessageOrNull(locale, "mail.mymail");
        validator.validateAndGetErrorMessageOrNull(locale, "my@mail.ml");
        validator.validateAndGetErrorMessageOrNull(locale, "_my@mail.ml");
    }
}
