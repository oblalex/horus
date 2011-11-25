package ua.cn.stu.oop.horus.web.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alex
 */
public class ApplicationContextHelper {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("classpath:/spring/webContext.xml");
        }
        return context;
    }

    public static <T> T getBeanByType(Class<T> type) {
        return getContext().getBean(type);
    }
}
