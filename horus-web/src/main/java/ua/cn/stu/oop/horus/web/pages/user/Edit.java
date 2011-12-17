package ua.cn.stu.oop.horus.web.pages.user;

import org.apache.shiro.authz.annotation.*;
import org.apache.tapestry5.EventContext;
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
import ua.cn.stu.oop.horus.core.util.time.TimeZoneUtil;

@RequiresUser
public class Edit{
    
    private static final int ON_SUCCESS_REDIRECT_TIMEOUT = 3;
    
    @Inject
    private SecurityService securityService;  
    
    private boolean visitorIsOwner;
    
    @Component
    private AccountComponent accountCmpnt;
    
    private Long userId;
    
    Object onActivate(EventContext context) {        
        userId = context.get(Long.class, 0);      

        if (userId==null){
            onActivateWithoutContext();
        } else {
            return onActivateWithContext();
        }
        
        return null;
    }
    
    void onActivateWithoutContext() {
        String visitorLogin = getVisitorLogin();
        User usr = accountCmpnt.getUserService().getUserOrNullByLogin(visitorLogin);
        accountCmpnt.setUser(usr);
        initFields();
        visitorIsOwner = true;
    }
    
    @RequiresRoles(UserRoles.ADMIN)
    Object onActivateWithContext() {        
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
        
        AvailableLocale aLoc = accountCmpnt.getLang();
        
        accountCmpnt.prepareUser();
        accountCmpnt.finishUserCreation();
        accountCmpnt.getComponentResources().discardPersistentFieldChanges();

        MessagePageData data = accountCmpnt.getMessageData();
        
        String htmlMessage = WebMessages.getMessage("usr.account.edit.success.msg", aLoc);
        
        if (loginChanged && visitorIsOwner){
            htmlMessage += " "+WebMessages.getMessage("usr.account.relogin.msg", aLoc);
            securityService.getSubject().logout();
            
            data.setCanGoForward(false);
        } else {
            data.setAutoRedirectForwardTimeoutSec(ON_SUCCESS_REDIRECT_TIMEOUT);
            data.setCanGoForward(true);
            if (visitorIsOwner){
                data.setNextPageURL("user/view");
            } else {
                data.setNextPageURL("user/view/"+accountCmpnt.getUser().getId());
            }
        }
       
        data.setPageTitleTail(WebMessages.getMessage("usr.account.edit.success", aLoc));
        data.setHtmlMessage(htmlMessage);        
        data.setType(MessagePageData.MessageType.SUCCESS);
        data.setLocale(aLoc);
        data.setCanGoBackward(false);
        
        Message mp = accountCmpnt.getMessagePage();
        mp.setMessageData(data);
                
        return mp;
    }        
    
    public String getPageTitle() {
        return WebMessages.getMessage("usr.account.edit.process", accountCmpnt.getLocale())+" - "+accountCmpnt.getUser().getLogin();
    }
    
    public String getSubmitTitle() {
        return WebMessages.getMessage("apply", accountCmpnt.getLocale());
    }

    public boolean getVisitorIsOwner() {
        return visitorIsOwner;
    }        
}
