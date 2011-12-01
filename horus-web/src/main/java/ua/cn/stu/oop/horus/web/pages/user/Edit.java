package ua.cn.stu.oop.horus.web.pages.user;

import org.apache.shiro.authz.annotation.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.components.user.AccountComponent;
import ua.cn.stu.oop.horus.web.pages.Index;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.util.*;
import ua.cn.stu.oop.horus.web.util.pages.*;
import ua.cn.stu.oop.horus.web.util.time.TimeZoneUtil;

@RequiresUser
public class Edit{
    
    @Inject
    private SecurityService securityService;  
    
    private boolean visitorIsOwner;
    
    @Component
    private AccountComponent accountCmpnt;
    
    void onActivate() {
        String visitorLogin = getVisitorLogin();
        User usr = accountCmpnt.getUserService().getUserOrNullByLogin(visitorLogin);
        accountCmpnt.setUser(usr);
        initFields();
        
        visitorIsOwner = true;
    }
    
    @RequiresRoles(UserRoles.ADMIN)
    Object onActivate(Long userId) {        
        User usr = accountCmpnt.getUserService().getEntityOrNullById(userId);
        
        if (usr == null) {
            return Index.class;
        }
        
        accountCmpnt.setUser(usr);        
        initFields();
        
        String visitorLogin = getVisitorLogin();
        visitorIsOwner = (usr.getLogin().equals(visitorLogin));
        
        return null;
    }
    
    private String getVisitorLogin(){
        return (String) securityService.getSubject().getPrincipal();
    }
    
    private void initFields(){
        User usr = accountCmpnt.getUser();
        accountCmpnt.setLogin(usr.getLogin());
        accountCmpnt.setEmail(usr.getEmail());
        accountCmpnt.setTimeZone(TimeZoneUtil.toString(usr.getTimeZoneUTC()));
        accountCmpnt.setLang(usr.getPreferredLocale());
    }
            
    void onValidate(){
        accountCmpnt.getTheForm().clearErrors();
        validateFields();               
    }
    
    private void validateFields(){
        validateLoginOnDemand();
        validatePasswordOnDemand();
        validateEmailOnDemand();      
    }

    private void validateLoginOnDemand() {
        if (accountCmpnt.getLogin().equals(accountCmpnt.getUser().getLogin())){
            return;
        }
        accountCmpnt.validateLogin();
    }

    private void validatePasswordOnDemand() {
        if (accountCmpnt.getPassword()==null) {
            return;
        }
        accountCmpnt.validatePassword();
    }

    private void validateEmailOnDemand() {
        if (accountCmpnt.getEmail().equals(accountCmpnt.getUser().getEmail())){
            return;
        }
        accountCmpnt.validateEmail();
    }
    
    Object onSuccess(){
        boolean loginChanged = (accountCmpnt.getUser().getLogin().equals(accountCmpnt.getLogin())==false);
        
        accountCmpnt.prepareUser();
        accountCmpnt.finishUserCreation();

        AvailableLocale aLoc = accountCmpnt.getLang();
        
        String htmlMessage = Messages.getMessage("usr.account.edit.success.msg", aLoc);
        
        if (loginChanged && visitorIsOwner){
            htmlMessage += " "+Messages.getMessage("usr.account.relogin.msg", aLoc);
            securityService.getSubject().logout();
        }        
        
        accountCmpnt.getComponentResources().discardPersistentFieldChanges();
        
        MessagePageData data = accountCmpnt.getMessageData();
        
        data.setPageTitleTail(Messages.getMessage("usr.account.edit.success", aLoc));
        data.setHtmlMessage(htmlMessage);        
        data.setType(MessagePageData.MessageType.SUCCESS);
        data.setLocale(aLoc);
        data.setCanGoForward(false);
        data.setCanGoBackward(false);
        
        Message mp = accountCmpnt.getMessagePage();
        mp.setMessageData(data);
                
        return mp;
    }        
    
    public String getPageTitle() {
        return Messages.getMessage("usr.account.edit.process", accountCmpnt.getLocale())+" - "+accountCmpnt.getUser().getLogin();
    }
    
    public String getSubmitTitle() {
        return Messages.getMessage("apply", accountCmpnt.getLocale());
    } 
}
