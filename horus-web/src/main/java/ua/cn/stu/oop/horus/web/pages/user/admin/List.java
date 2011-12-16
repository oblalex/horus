package ua.cn.stu.oop.horus.web.pages.user.admin;

import java.util.Collection;
import javax.inject.Inject;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.services.Request;
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
    private Request request;
    
    @Inject
    @Autowired
    private UserAdminService adminService;
    
    @Persist
    @Property
    private UserAdmin item;
    
    @Persist
    @Property
    private String commentVal;
    
    private UserAdmin visitorAdmin = null;

    @Component
    private Form editForm;
    
    @Component(id = "listZone")
    private Zone listZone;
    
    @Component(id = "editZone")
    private Zone editZone;
    
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
    
    public Object onActionFromDeleteItem(Long itemId) {
        adminService.deleteEntityById(itemId);
        return getListZone();
    }

    public Object getListZone() {
        return request.isXHR() ? listZone.getBody() : null;
    }
    
    public Object onActionFromDialogajaxlink(Long itemId) {
        item = adminService.getEntityOrNullById(itemId);
        commentVal = item.getComment();
        return getEditZone();
    }
    
    public void onSubmitFromEditForm() {
        item.setComment(commentVal);
        adminService.saveOrUpdateEntity(item);
    }
    
    public Object getEditZone() {
        return request.isXHR() ? editZone.getBody() : null;
    }
    
    @Override
    public String getPageTitle() {
        return WebMessages.getMessage("admin.list", getLocale());
    }    
}
