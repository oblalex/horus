package ua.cn.stu.oop.horus.web.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/webContext.xml"})
public abstract class GenericSpringTest extends AbstractTransactionalJUnit4SpringContextTests {    
}
