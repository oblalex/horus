package ua.cn.stu.oop.horus.core.service.user;

import org.apache.commons.logging.*;
import org.apache.shiro.util.ByteSource;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.util.DateTimeUtil;
import ua.cn.stu.oop.horus.web.util.EncodingUtil;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/coreContext.xml"})
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final static Log log = LogFactory.getLog(UserServiceTest.class);
    private final static String LOGIN_ADMIN = "admin";
    @Autowired
    UserService userService;

    //@Test
    public void testCreateUserAdminIfDontExists() {
        User user = userService.getUserOrNullByLogin(LOGIN_ADMIN);
        if (user == null) {
            user = new User();
            user.setLogin(LOGIN_ADMIN);
            user.setPreferredLocale(AvailableLocale.ru);
            user.setRegistrationDate(DateTimeUtil.getTimestampNow());
            user.setEmail("nomail");
            
            ByteSource byteSource = EncodingUtil.getRandomSaltSource();
            
            user.setSalt(byteSource.getBytes());
            user.setHashedPassword(EncodingUtil.encodeStringUsingSaltSource("1111", byteSource));
            
            Long id = userService.saveAndGetId(user);
            log.debug("Created user id: " + id);
        }
    }

    @Test
    public void testIsUserBannedNow() {
        User user = userService.getUserOrNullByLogin(LOGIN_ADMIN);
        if (user != null) {
            assertEquals(false, userService.isUserBannedNow(user));
        }
    }

    @Test
    public void testGetUserOrNullByLogin() {
        User user = userService.getUserOrNullByLogin(LOGIN_ADMIN);
        //assertEquals(null, user);
    }

    @Test
    public void testGetUserRoles() {
        User user = userService.getUserOrNullByLogin(LOGIN_ADMIN);
        if (user != null) {
            StringBuilder userRoles = new StringBuilder();
            boolean need_comma = false;
            for (String role : userService.getUserRoles(user)) {
                if (need_comma) {
                    userRoles.append(", ");
                } else {
                    need_comma = true;
                }
                userRoles.append(role);
            }
            log.debug(userRoles.toString());
        }
    }
}
