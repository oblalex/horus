package ua.cn.stu.oop.horus.web.pages.user;

import ua.cn.stu.oop.horus.web.base.user.AccountPage;
import org.apache.shiro.authz.annotation.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.web.pages.Index;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.pages.store.DBStore;
import ua.cn.stu.oop.horus.web.util.*;
import ua.cn.stu.oop.horus.web.util.pages.*;
import ua.cn.stu.oop.horus.web.util.time.TimeZoneUtil;

@RequiresUser
public class Edit extends AccountPage{
    
    @Inject
    private SecurityService securityService;
    
    @InjectPage
    private DBStore dbStore;    
    
    private boolean visitorIsOwner;
    
    void onActivate() {
        String visitorLogin = getVisitorLogin();
        User usr = getUserService().getUserOrNullByLogin(visitorLogin);
        setUser(usr);
        initFields();
        
        visitorIsOwner = true;
    }
    
    @RequiresRoles(UserRoles.ADMIN)
    Object onActivate(Long userId) {        
        User usr = getUserService().getEntityOrNullById(userId);
        
        if (usr == null) {
            return Index.class;
        }
        
        setUser(usr);        
        initFields();
        
        String visitorLogin = getVisitorLogin();
        visitorIsOwner = (usr.getLogin().equals(visitorLogin));
        
        return null;
    }
    
    private String getVisitorLogin(){
        return (String) securityService.getSubject().getPrincipal();
    }
    
    private void initFields(){
        User usr = getUser();
        setLogin(usr.getLogin());
        setEmail(usr.getEmail());
        setTimeZone(TimeZoneUtil.toString(usr.getTimeZoneUTC()));
        setLang(usr.getPreferredLocale());
    }
            
    void onValidate(){
        getTheForm().clearErrors();
        validateFields();               
    }
    
    private void validateFields(){
        validateLoginOnDemand();
        validatePasswordOnDemand();
        validateEmailOnDemand();      
    }

    private void validateLoginOnDemand() {
        if (getLogin().equals(getUser().getLogin())){
            return;
        }
        validateLogin();
    }

    private void validatePasswordOnDemand() {
        if (getPassword()==null) {
            return;
        }
        validatePassword();
    }

    private void validateEmailOnDemand() {
        if (getEmail().equals(getUser().getEmail())){
            return;
        }
        validateEmail();
    }
    
    Object onSuccess(){
        boolean loginChanged = (getUser().getLogin().equals(getLogin())==false);
        
        prepareUser();
        finishUserCreation();

        String htmlMessage = Messages.getMessage("usr.account.edit.success.msg", getLang());
        
        if (loginChanged && visitorIsOwner){
            htmlMessage += " "+Messages.getMessage("usr.account.relogin.msg", getLang());
            securityService.getSubject().logout();
        }        
        
        getComponentResources().discardPersistentFieldChanges();
        
        MessagePageData data = getMessageData();

        data.setPageTitleTail(Messages.getMessage("usr.account.edit.success", getLang()));
        data.setHtmlMessage(htmlMessage);        
        data.setType(MessagePageData.MessageType.SUCCESS);
        data.setLocale(getLang());
        data.setCanGoForward(false);
        data.setCanGoBackward(false);
        
        Message mp = getMessagePage();
        mp.setMessageData(data);
                
        return mp;
    }        
    
    @Override
    public String getPageTitle() {
        return Messages.getMessage("usr.account.edit.process", getLocale())+" - "+getUser().getLogin();
    }
    
    @Override
    public String getAvatarUri() {
        DBFile ava = getUser().getAvatar();
        
        if ((ava!=null)&&(getIsAvatarUploaded()==false)){
            return dbStore.getUriFileInDB(ava.getId());
        }
              
        return getUploadedAvatarUri();
    }
}
