package ua.cn.stu.oop.horus.core.service.Impl.text;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.*;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.language.*;
import ua.cn.stu.oop.horus.core.service.text.*;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/coreContext.xml"})
public class LocalizedTitleServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    TitleLinkService titleLinkService;
    @Autowired
    LocalizedTitleService localizedTitleService;

    @Test
    public void testGetUserPicturesDirectory() {
        for (String s : localizedTitleService.getMainTitlesForLocaleStartingWithString(AvailableLocale.en, "My ")) {
            System.out.println(s);
        }
    }    
}
