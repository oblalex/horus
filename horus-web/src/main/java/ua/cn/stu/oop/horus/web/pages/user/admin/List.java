package ua.cn.stu.oop.horus.web.pages.user.admin;

import java.util.Collection;
import javax.inject.Inject;
import org.apache.tapestry5.annotations.Property;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.user.UserAdmin;
import ua.cn.stu.oop.horus.core.service.user.UserAdminService;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.util.WebMessages;

/**
 *
 * @author alex
 */
public class List extends GenericPage{

    @Inject
    @Autowired
    private UserAdminService adminService;
    
    @Property
    private UserAdmin item;
        
    public Collection<UserAdmin> getAdmins() {
        return adminService.getAllEntites();
    }
    
    @Override
    public String getPageTitle() {
        return WebMessages.getMessage("admin.list", getLocale());
    }
    
}
