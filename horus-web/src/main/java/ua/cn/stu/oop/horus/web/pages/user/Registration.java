package ua.cn.stu.oop.horus.web.pages.user;

import ua.cn.stu.oop.horus.web.base.user.AccountPage;
import java.sql.Timestamp;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.util.*;
import ua.cn.stu.oop.horus.web.util.mail.*;
import ua.cn.stu.oop.horus.web.util.pages.*;
import ua.cn.stu.oop.horus.web.util.pages.validator.*;
import ua.cn.stu.oop.horus.web.util.time.TimeZoneUtil;

@RequiresGuest
public class Registration extends AccountPage{
    
    @Inject
    @Autowired
    private MailService mailService;    

    @Inject
    @Autowired
    private LoginValidator loginValidator;
    
    @Inject
    @Autowired
    private PasswordValidator passwordValidator;
    
    @Inject
    @Autowired
    private EmailValidator emailValidator;    
    
    @Inject
    private ComponentResources componentResources;   
    
    @Inject
    private HttpServletRequest httpRequest;
              
    @Component
    private Form registrationForm;
    
    @Component(id = "formZone")
    private Zone formZone;    
    
    private boolean isBlockSet = false;
        
    void onValidate(){
        registrationForm.clearErrors();
        validateFields();
        
        if (registrationForm.getHasErrors()) return;    
        if (isBlockSet) return;
        isBlockSet = true;
        
        prepareUser();        
        trySendRegistrationNotificationMail();
    }
    
    private void validateFields(){
        validateLogin();
        validatePassword();
        validateEmail();
    }

    private void validateLogin() {
        registrationForm.recordError(
                getLoginField(),
                loginValidator.validateAndGetErrorMessageOrNull(
                    getLocale(), getLogin()));
    }

    private void validatePassword() {
        registrationForm.recordError(
                getPasswordField(),
                passwordValidator.validateAndGetErrorMessageOrNull(
                    getLocale(), getPassword(), getPasswordConfirm()));
    }

    private void validateEmail() {
        registrationForm.recordError(
                getEmailField(),
                emailValidator.validateAndGetErrorMessageOrNull(
                    getLocale(), getEmail()));        
    }    
    
    private void trySendRegistrationNotificationMail(){               
        try {
            mailService.sendMail(new RegistrationNotifyMail(
                                 getUser(),
                                 getPassword(),
                                 HttpRequestHelper.getContextRootUrl(httpRequest)));
        } catch (MessagingException ex) {
            onMailSendFailure();
        }
    }

    private void onMailSendFailure() {
        registrationForm.recordError(Messages.getMessage("registration.failure.msg", getLang()));        
    }
    
    Object onSuccess(){
        finishUserCreation();

        componentResources.discardPersistentFieldChanges();
        
        MessagePageData data = getMessageData();        
        
        data.setType(MessagePageData.MessageType.SUCCESS);
        data.setPageTitleTail(Messages.getMessage("registration.success", getLang()));
        data.setHtmlMessage(Messages.getMessage("registration.success.msg", getLang()));        
        data.setLocale(getLang());
        data.setCanGoForward(false);
        data.setCanGoBackward(false);
        
        Message mp = getMessagePage();
        mp.setMessageData(data);
                
        return mp;
    }

    private void prepareUser(){
        User usr = new User();

        usr.setLogin(getLogin());
        usr.setEmail(getEmail());
        usr.setEmailConfirmed(false);
        usr.setPreferredLocale(getLang());
        usr.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        usr.setTimeZoneUTC(TimeZoneUtil.parseTimeZone(getTimeZone()));

        ByteSource bs = EncodingUtil.getRandomSaltSource();
        usr.setSalt(bs.getBytes());
        usr.setHashedPassword(new Sha1Hash(getPassword(), bs).toString());
        
        setUser(usr);
    }
    
    private void finishUserCreation(){        
        getUserService().saveOrUpdateEntity(getUser());
        try {
            setUserAvatar();
        } catch (Exception ex) {            
        }
    }          
    
    void onSubmit(){
        isBlockSet = false;
    }
    
    Object onFailure() {
        return getFormZone();
    }
    
    Object getFormZone() {
        return getRequest().isXHR() ? formZone.getBody() : null;
    }
        
    @Override
    public String getPageTitle() {
        return Messages.getMessage("registration", getLocale());
    }

    @Override
    public String getAvatarUri() {
        return getUploadedAvatarUri();
    }
}
