package ua.cn.stu.oop.horus.core.service.Impl.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.cn.stu.oop.horus.core.domain.file.DBFileDirectory;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.service.file.DBFileDirectoryService;
import ua.cn.stu.oop.horus.core.service.user.UserService;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/coreContext.xml"})
public class FileDirectoryServiceImplTest extends AbstractJUnit4SpringContextTests {
        
    @Autowired
    DBFileDirectoryService fileDirectoryService;
    @Autowired
    UserService userService;
    
    @Test
    public void testGetUserPicturesDirectory() {
        //User user = userService.getUserOrNullByLogin("admin");
        //DBFileDirectory directory = fileDirectoryService.getUserPicturesDirectory(user);
        //System.out.println(directory.getId());
    }
}
