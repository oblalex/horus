package ua.cn.stu.oop.horus.web.pages;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import ua.cn.stu.oop.horus.core.domain.user.UserRoles;
import ua.cn.stu.oop.horus.web.base.GenericPage;

/**
 *
 * @author alex
 */
@RequiresRoles(UserRoles.ADMIN)
public class Control  extends GenericPage{

    @Inject
    private Messages messages;
    
    @Override
    public String getPageTitle() {
        return messages.get("control.panel");
    }
    
}
