package ua.cn.stu.oop.horus.web.pages.user;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.components.user.AccountComponent;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.util.*;
import ua.cn.stu.oop.horus.web.util.mail.*;
import ua.cn.stu.oop.horus.web.util.pages.*;

@RequiresGuest
public class Registration {
    
    @Inject
    @Autowired
    private MailService mailService;            
    
    @Inject
    private HttpServletRequest httpRequest;                  
    
    @Component
    private AccountComponent accountCmpnt;
    
    private boolean isBlockSet = false;
        
    void onValidate(){
        Form frm = accountCmpnt.getTheForm();
        
        frm.clearErrors();
        validateFields();
        
        if (frm.getHasErrors()) return;    
        if (isBlockSet) return;
        isBlockSet = true;
        
        accountCmpnt.prepareUser();        
        trySendRegistrationNotificationMail();
    }
    
    private void validateFields(){
        accountCmpnt.validateLogin();
        accountCmpnt.validatePassword();
        accountCmpnt.validateEmail();
    }        
    
    private void trySendRegistrationNotificationMail(){               
        try {
            mailService.sendMail(new RegistrationNotifyMail(
                                 accountCmpnt.getUser(),
                                 accountCmpnt.getPassword(),
                                 HttpRequestHelper.getContextRootUrl(httpRequest)));
        } catch (MessagingException ex) {
            onMailSendFailure();
        }
    }

    private void onMailSendFailure() {
        accountCmpnt.getTheForm().recordError(Messages.getMessage("registration.failure.msg", accountCmpnt.getLang()));        
    }
    
    Object onSuccess(){
        accountCmpnt.finishUserCreation();

        accountCmpnt.getComponentResources().discardPersistentFieldChanges();
        
        MessagePageData data = accountCmpnt.getMessageData();        
        
        AvailableLocale aLoc = accountCmpnt.getLang();
        
        data.setType(MessagePageData.MessageType.SUCCESS);
        data.setPageTitleTail(Messages.getMessage("registration.success", aLoc));
        data.setHtmlMessage(Messages.getMessage("registration.success.msg", aLoc));        
        data.setLocale(aLoc);
        data.setCanGoForward(false);
        data.setCanGoBackward(false);
        
        Message mp = accountCmpnt.getMessagePage();
        mp.setMessageData(data);
                
        return mp;
    }
    
    void onSubmit(){
        isBlockSet = false;
    }        
        
    public String getPageTitle() {
        return Messages.getMessage("registration", accountCmpnt.getLocale());
    }
    
    public String getSubmitTitle() {
        return Messages.getMessage("register.me", accountCmpnt.getLocale());
    } 
}
