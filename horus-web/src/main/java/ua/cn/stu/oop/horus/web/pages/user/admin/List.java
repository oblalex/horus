package ua.cn.stu.oop.horus.web.pages.user.admin;

import java.util.Collection;
import javax.inject.Inject;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.tynamo.security.services.SecurityService;
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
    private SecurityService securityService;
    
    @Inject
    @Autowired
    private UserAdminService adminService;
    
    @Property
    private UserAdmin item;
    
    private UserAdmin visitorAdmin = null;
    
    @Component(id = "listZone")
    private Zone listZone;
     
    public void beginRender() {
        String visitorLogin = getVisitorLogin();
        if (visitorLogin==null){
            return;
        }
        visitorAdmin = adminService.getAdminOrNullByUserLogin(visitorLogin);
    }
    
    public Collection<UserAdmin> getAdmins() {
        return adminService.getAllEntites();
    }

    private String getVisitorLogin(){
        return (String) securityService.getSubject().getPrincipal();
    }
    
    public boolean getItemPepresentsVisitor(){
        if (visitorAdmin==null){
            return false;
        }
        return visitorAdmin.getId().equals(item.getId());
    }
    
    @Override
    public String getPageTitle() {
        return WebMessages.getMessage("admin.list", getLocale());
    }    
}
