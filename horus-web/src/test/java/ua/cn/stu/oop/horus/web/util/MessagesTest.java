package ua.cn.stu.oop.horus.web.util;

import org.junit.Test;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public class MessagesTest{


    //@Test
    public void testGetMessage() {
        System.out.println(WebMessages.getMessage("index", AvailableLocale.ru));
        System.out.println(WebMessages.getMessage("index", AvailableLocale.en));
        System.out.println(WebMessages.getMessageParameterized("mail.newreg.subj", AvailableLocale.en, "LOL"));
    }
}
