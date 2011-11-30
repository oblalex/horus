package ua.cn.stu.oop.horus.web.pages.user;

import ua.cn.stu.oop.horus.web.base.user.AccountPage;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.UserRoles;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.pages.store.DBStore;
import ua.cn.stu.oop.horus.web.util.*;
import ua.cn.stu.oop.horus.web.util.pages.*;
import ua.cn.stu.oop.horus.web.util.pages.validator.*;
import ua.cn.stu.oop.horus.web.util.time.TimeZoneUtil;

@RequiresUser
public class Edit extends AccountPage{
    
    @Inject
    private SecurityService securityService;
    
    @Inject
    @Autowired
    private LoginValidator loginValidator;
    
    @Inject
    @Autowired
    private PasswordValidator passwordValidator;
    
    @Inject
    @Autowired
    private EmailValidator emailValidator;
    
    @InjectPage
    private DBStore dbStore;    
    
    @Inject
    private ComponentResources componentResources;
    
    @Component
    private Form editForm;
    
    @Component(id = "formZone")
    private Zone formZone;
    
    void onActivate() {
        String loginPrincipal = (String) securityService.getSubject().getPrincipal();
        User usr = getUserService().getUserOrNullByLogin(loginPrincipal);
        setUser(usr);
        initFields();
    }
    
    @RequiresRoles(UserRoles.ADMIN)
    void onActivate(Long userId) {
        User usr = getUserService().getEntityOrNullById(userId);
        setUser(usr);
        // TODO : if user was not found
        initFields();
    }
    
    private void initFields(){
        User usr = getUser();
        setLogin(usr.getLogin());
        setEmail(usr.getEmail());
        setTimeZone(TimeZoneUtil.toString(usr.getTimeZoneUTC()));
        setLang(usr.getPreferredLocale());
    }
            
    void onValidate(){
        editForm.clearErrors();
        validateFields();               
    }
    
    private void validateFields(){
        validateLogin();
        validatePassword();
        validateEmail();      
    }

    private void validateLogin() {
        if (getLogin().equals(getUser().getLogin())){
            return;
        }
        editForm.recordError(
                getLoginField(),
                loginValidator.validateAndGetErrorMessageOrNull(
                    getLocale(), getLogin()));
    }

    private void validatePassword() {
        if (getPassword()==null) {
            return;
        }
        editForm.recordError(
                getPasswordField(),
                passwordValidator.validateAndGetErrorMessageOrNull(
                    getLocale(), getPassword(), getPasswordConfirm()));
    }

    private void validateEmail() {
        if (getEmail().equals(getUser().getEmail())){
            return;
        }
        editForm.recordError(
                getEmailField(),
                emailValidator.validateAndGetErrorMessageOrNull(
                    getLocale(), getEmail()));
    }
    
    Object onSuccess(){
        prepareUser();
        finishUserCreation();

        // TODO: if user is owner and login was changed -> relogin
        
        componentResources.discardPersistentFieldChanges();
        
        MessagePageData data = getMessageData();
        
        data.setType(MessagePageData.MessageType.SUCCESS);
        data.setPageTitleTail(Messages.getMessage("usr.account.edit.success", getLang()));
        data.setHtmlMessage(Messages.getMessage("usr.account.edit.success.msg", getLang()));
        data.setLocale(getLang());
        data.setCanGoForward(false);
        data.setCanGoBackward(false);
        
        Message mp = getMessagePage();
        mp.setMessageData(data);
                
        return mp;
    }

    private void prepareUser(){
        User usr = getUser();
        usr.setLogin(getLogin());
        usr.setEmail(getEmail());
        usr.setPreferredLocale(getLang());
        usr.setTimeZoneUTC(TimeZoneUtil.parseTimeZone(getTimeZone()));
        
        if (getPassword()!=null){
            usr.setHashedPassword(new Sha1Hash(getPassword(), usr.getSalt()).toString());
        }
    }
    
    private void finishUserCreation(){        
        getUserService().saveOrUpdateEntity(getUser());
        try {
            setUserAvatar();
        } catch (Exception ex) {            
        }
    }           
    
    Object onFailure() {
        return getFormZone();
    }
    
    Object getFormZone() {
        return getRequest().isXHR() ? formZone.getBody() : null;
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
