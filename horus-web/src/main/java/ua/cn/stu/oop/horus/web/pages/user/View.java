package ua.cn.stu.oop.horus.web.pages.user;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.service.user.*;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.pages.Index;
import ua.cn.stu.oop.horus.web.pages.store.DBStore;
import ua.cn.stu.oop.horus.web.util.WebMessages;
import ua.cn.stu.oop.horus.core.util.time.*;

/**
 *
 * @author alex
 */
public class View extends GenericPage {
    
    @Inject
    @Autowired
    private UserService userService;
    
    @Inject
    @Autowired
    private UserAdminService userAdminService;
    
    @Inject
    @Autowired
    private UserBanService userBanService;
    
    @Inject
    private SecurityService securityService;
    
    @InjectPage
    private DBStore dbStore;
    
    @Inject
    private Request request;
    
    @Property
    private User user;
    
    private User visitor;
    
    @Property
    private UserAdmin userAdmin;
    
    @Property
    private boolean visitorCanEdit;
    
    @Property
    private boolean visitorIsOwner;
    
    @Property
    private Long userId;   
    
    @Component(id = "aboutAdminZone")
    private Zone underAvaZone;
    
    Object onActivate(EventContext context) {
        this.userId = context.get(Long.class, 0);
        
        if (userId==null){
            onActivateWithoutContext();
        } else {
            return onActivateWithContext();
        }
        
        return null;
    }
    
    @RequiresUser
    void onActivateWithoutContext() {
        obtainVisitor();
        this.userId = visitor.getId();
        this.user=visitor;
        visitorCanEdit=true;
        visitorIsOwner=true;
        obtainAdmin();
    }
    
    Object onActivateWithContext() {
        
        obtainUser();
        
        if (user==null){
            return Index.class;
        }
        
        obtainAdmin();
        obtainVisitor();
        checkPermisions();
        return null;
    }
    
    private void obtainUser(){
        user = userService.getEntityOrNullById(userId);
    }
    
    private void obtainAdmin(){
        userAdmin = userAdminService.getAdminOrNullByUser(user);
    }
    
    private void obtainVisitor(){
        String visitorLogin = (String) securityService.getSubject().getPrincipal();
        
        if (visitorLogin==null){
            visitor = null;
        } else {
            visitor = userService.getUserOrNullByLogin(visitorLogin);
        }
    }
    
    private void checkPermisions(){
        checkIsOwner();
        checkCanEdit();
    }
    
    private void checkIsOwner(){        
        if (visitor==null){
            visitorIsOwner = false;
            return;
        }
        
        visitorIsOwner = (user.getId().equals(visitor.getId()));        
    }
    
    private void checkCanEdit(){
        if (visitor==null){
            visitorCanEdit = false;
            return;
        }
        
        if (visitorIsOwner){                        
            visitorCanEdit = 
                    user.isEmailConfirmed()
                    && (userBanService.isUserBannedNow(user)==false);
            return;
        }
        
        visitorCanEdit = (userAdminService.getAdminOrNullByUser(visitor)!=null);
    }
    
    public String getAvatarUri(){
        DBFile ava = user.getAvatar();
        if (ava==null) return null;
        return dbStore.getUriFileInDB(ava.getId());
    }
    
    @Override
    public String getPageTitle() {
        return user.getLogin()+" - "+WebMessages.getMessage("usr.account", getLocale());
    }    
    
    public String getSignedTimeZone(){
        return TimeZoneUtil.toString(user.getTimeZoneUTC());
    }
    
    public String getLang(){
        return WebMessages.getMessage(user.getPreferredLocale().name(), getLocale());
    }
    
    public String getRegistrationDate(){
        return DateTimeFormater.formateCommonDate(user.getRegistrationDate());
    }

    public Object getUnderAvaZone() {
        return request.isXHR() ? underAvaZone.getBody() : null;
    }        
}
