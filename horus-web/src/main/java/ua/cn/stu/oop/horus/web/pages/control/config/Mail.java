package ua.cn.stu.oop.horus.web.pages.control.config;

import org.apache.shiro.authz.annotation.RequiresRoles;
import ua.cn.stu.oop.horus.core.domain.user.UserRoles;

/**
 *
 * @author alex
 */
@RequiresRoles(UserRoles.ADMIN)
public class Mail {
    
}
