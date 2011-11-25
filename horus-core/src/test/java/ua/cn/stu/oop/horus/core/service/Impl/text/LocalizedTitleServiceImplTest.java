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
    final String blueTitle = "Синяя";
    LocalizedTitle localizedTitleBlue;

    @Test
    public void testCheckAndAddLocalizedTitleBlue() {
//        localizedTitleBlue =
//                (LocalizedTitle) localizedTitleService.getEntityOrNullByTitle(blueTitle);
//        if (localizedTitleBlue == null) {
//            addLocalizedTitleBlue();
//        }
    }

    private void addLocalizedTitleBlue() {

        TitleLink titleLink = createNewTitleLink();

        localizedTitleBlue = new LocalizedTitle();
        localizedTitleBlue.setGrammaticalCase(GrammaticalCase.NOMINATIVE);
        localizedTitleBlue.setGrammaticalGender(GrammaticalGender.FEMALE);
        localizedTitleBlue.setGrammaticalNumber(GrammaticalNumber.SINGULAR);
        localizedTitleBlue.setLocale(AvailableLocale.ru);
        localizedTitleBlue.setPartOfSpeech(PartOfSpeech.ADJECTIVE);
        localizedTitleBlue.setTitle(blueTitle);
        localizedTitleBlue.setTitleLink(titleLink);

        localizedTitleService.saveAndGetId(localizedTitleBlue);
    }

    private TitleLink createNewTitleLink() {
        TitleLink titleLink = new TitleLink();
        titleLinkService.saveAndGetId(titleLink);
        return titleLink;
    }
}
