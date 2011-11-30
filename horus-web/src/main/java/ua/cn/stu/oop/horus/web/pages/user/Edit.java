package ua.cn.stu.oop.horus.web.pages.user;

import ua.cn.stu.oop.horus.web.base.user.AccountPage;
import java.io.*;
import java.sql.Timestamp;
import org.apache.commons.io.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.UserRoles;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.file.*;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.pages.store.DBStore;
import ua.cn.stu.oop.horus.web.pages.store.UploadStore;
import ua.cn.stu.oop.horus.web.util.*;
import ua.cn.stu.oop.horus.web.util.pages.*;
import ua.cn.stu.oop.horus.web.util.pages.validator.*;
import ua.cn.stu.oop.horus.web.util.file.FileMimeTypeChecker;
import ua.cn.stu.oop.horus.web.util.image.ImageInFileUtil;
import ua.cn.stu.oop.horus.web.util.time.TimeZoneUtil;

@RequiresUser
public class Edit extends AccountPage{
    
    private User user;
    
    @Inject
    @Autowired
    private UserService userService;
    
    @Inject
    @Autowired
    private DBFileService fileService;
    
    @Inject
    @Autowired
    private DBFileDirectoryService fileDirectoryService;
        
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
    private UploadStore uploadStore;
    
    @InjectPage
    private DBStore dbStore;    
    
    @Inject
    private ComponentResources componentResources;
    
    @Inject
    private Request request;
        
    @Component
    private Form editForm;
    
    @Component(id = "formZone")
    private Zone formZone;
    
    @Component(id = "avaZone")
    private Zone avaZone;
    
    @Persist
    private UploadedFile uploadedAvatar;
    
    @Persist
    private File copied;

    void onActivate(AvailableLocale lang, String timeZone) {
        setLang(lang);
        setTimeZone(timeZone);
    }
    
    void onActivate() {
        String loginPrincipal = (String) securityService.getSubject().getPrincipal();
        user = userService.getUserOrNullByLogin(loginPrincipal);
        initFields();
    }
    
    @RequiresRoles(UserRoles.ADMIN)
    void onActivate(Long userId) {
        user = userService.getEntityOrNullById(userId);
        // TODO : if user was not found
        initFields();
    }
    
    private void initFields(){
        setLogin(user.getLogin());
        setEmail(user.getEmail());
        setTimeZone(TimeZoneUtil.toString(user.getTimeZoneUTC()));
        setLang(user.getPreferredLocale());
    }
    
    @OnEvent(component = "uploadAvatar", value = JQueryEventConstants.AJAX_UPLOAD)
    Object onUploadAvatar(UploadedFile uploadedFile) throws IOException {
        uploadedAvatar = uploadedFile;

        copied = new File(ConfigContainer.CONFIG.TMP.getPathToTemporary(uploadedAvatar.getFileName()));
        uploadedAvatar.write(copied);

        return getAvaZone();
    }
    
    Object onActionFromAvatarCancel() {
        copied = null;
        uploadedAvatar = null;
        return getAvaZone();
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
        if (getLogin().equals(user.getLogin())){
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
        if (getEmail().equals(user.getEmail())){
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
        user.setLogin(getLogin());
        user.setEmail(getEmail());
        user.setPreferredLocale(getLang());
        user.setTimeZoneUTC(TimeZoneUtil.parseTimeZone(getTimeZone()));
        
        if (getPassword()!=null){
            user.setHashedPassword(new Sha1Hash(getPassword(), user.getSalt()).toString());
        }
    }
    
    private void finishUserCreation(){        
        userService.saveOrUpdateEntity(user);
        try {
            setUserAvatar();
        } catch (Exception ex) {            
        }
    }

    private void setUserAvatar() throws Exception {
        
        if (uploadedAvatar == null) {
            return;
        }
        
        cropAndScale();

        DBFile avatarDBfile = user.getAvatar();
        
        if (avatarDBfile==null){
            avatarDBfile = new DBFile();
        }
        
        avatarDBfile.setContentType(FileMimeTypeChecker.getFileMimeType(copied));
        avatarDBfile.setTitle("avatar-" + user.getLogin() + "." + FilenameUtils.getExtension(uploadedAvatar.getFileName()));
        avatarDBfile.setLastModificationTime(new Timestamp(System.currentTimeMillis()));
        avatarDBfile.setData(FileUtils.readFileToByteArray(copied));
        avatarDBfile.setUser(user);
        avatarDBfile.setDirectory(fileDirectoryService.getUserPicturesDirectory(user));
        fileService.saveOrUpdateEntity(avatarDBfile);
        user.setAvatar(avatarDBfile);
        userService.saveOrUpdateEntity(user);
    }
    
    private void cropAndScale()  throws IOException, InterruptedException, IM4JavaException {        
        cropAvatar();
        scaleAvatar();
    }

    private void cropAvatar() throws IOException, InterruptedException, IM4JavaException {

        int widthMax = Integer.parseInt(request.getParameter("avatarWidthMax"));
        int realWidth = Integer.parseInt(request.getParameter("avatarWidth"));
        int selectionX = Integer.parseInt(request.getParameter("avatarSelectionX"));
        int selectionY = Integer.parseInt(request.getParameter("avatarSelectionY"));
        int selectionWidth = Integer.parseInt(request.getParameter("avatarSelectionWidth"));
        int selectionHeight = Integer.parseInt(request.getParameter("avatarSelectionHeight"));

        if ((selectionWidth > 0) && (selectionHeight > 0)) {
            if (realWidth > widthMax) {

                double avatarWidthD = realWidth;
                double avatarWidthMaxD = widthMax;
                double scaleCoef = avatarWidthD / avatarWidthMaxD;

                selectionX = (int) Math.round(selectionX * scaleCoef);
                selectionY = (int) Math.round(selectionY * scaleCoef);
                selectionWidth = (int) Math.round(selectionWidth * scaleCoef);
                selectionHeight = (int) Math.round(selectionHeight * scaleCoef);
            }                        
            
            ImageInFileUtil.crop(
                    copied,
                    selectionWidth,
                    selectionHeight,
                    selectionX,
                    selectionY);
        }
    }

    private void scaleAvatar() throws IOException, InterruptedException, IM4JavaException {
        ImageInFileUtil.scale(copied, 100, 100);
    }
    
    Object onFailure() {
        return getFormZone();
    }
    
    Object getAvaZone() {
        return request.isXHR() ? new MultiZoneUpdate("avaZone", avaZone.getBody()) : null;
    }
    
    Object getFormZone() {
        return request.isXHR() ? formZone.getBody() : null;
    }
    
    public String getUploadedAvatarUri() {
        DBFile ava = user.getAvatar();
        
        if ((ava!=null)&&(uploadedAvatar==null)){
            return dbStore.getUriFileInDB(ava.getId());
        }
                
        if (copied == null) {
            return null;
        }
        
        return uploadStore.getUriUploadedFile(copied.getName());
    }

    @Override
    public String getPageTitle() {
        return Messages.getMessage("usr.account.edit.process", getLocale())+" - "+user.getLogin();
    } 
}
