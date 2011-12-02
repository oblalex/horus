package ua.cn.stu.oop.horus.core.service.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.util.GenericSpringTest;

/**
 *
 * @author alex
 */
public class UserAdminServiceTest extends GenericSpringTest {
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserAdminService adminService;
    
    @Test
    public void testGetAdminOrNullByUser() {
        User user = userService.getUserOrNullByLogin("admin");
        UserAdmin admin = adminService.getAdminOrNullByUser(user);
        System.out.println(admin==null);
    }
}
