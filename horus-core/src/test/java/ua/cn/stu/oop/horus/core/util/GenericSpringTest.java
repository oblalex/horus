package ua.cn.stu.oop.horus.core.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/coreContext.xml"})
public abstract class GenericSpringTest extends AbstractTransactionalJUnit4SpringContextTests {    
}
