package ua.cn.stu.oop.horus.web.pages.user;

import ua.cn.stu.oop.horus.web.base.user.AccountPage;
import java.io.*;
import java.sql.Timestamp;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.*;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.util.ByteSource;
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
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.file.*;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.pages.store.UploadStore;
import ua.cn.stu.oop.horus.web.util.*;
import ua.cn.stu.oop.horus.web.util.mail.*;
import ua.cn.stu.oop.horus.web.util.pages.*;
import ua.cn.stu.oop.horus.web.util.pages.validator.*;
import ua.cn.stu.oop.horus.web.util.file.FileMimeTypeChecker;
import ua.cn.stu.oop.horus.web.util.image.ImageInFileUtil;
import ua.cn.stu.oop.horus.web.util.time.TimeZoneUtil;

@RequiresGuest
public class Registration extends AccountPage{

    private User user;
    
    @Inject
    @Autowired
    private MailService mailService;    
    
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
    
    @Inject
    private ComponentResources componentResources;
    
    @Inject
    private Request request;
    
    @Inject
    private HttpServletRequest httpRequest;
              
    @Component
    private Form registrationForm;
    
    @Component(id = "formZone")
    private Zone formZone;
    
    @Component(id = "avaZone")
    private Zone avaZone;
    
    @Persist
    private UploadedFile uploadedAvatar;
        
    @Persist
    private File copied;
    
    private boolean isBlockSet = false;

    void onActivate(AvailableLocale lang, String timeZone) {
        setLang(lang);
        setTimeZone(timeZone);
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
                                 user,
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
        user = new User();

        user.setLogin(getLogin());
        user.setEmail(getEmail());
        user.setEmailConfirmed(false);
        user.setPreferredLocale(getLang());
        user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        user.setTimeZoneUTC(TimeZoneUtil.parseTimeZone(getTimeZone()));

        ByteSource bs = EncodingUtil.getRandomSaltSource();
        user.setSalt(bs.getBytes());
        user.setHashedPassword(new Sha1Hash(getPassword(), bs).toString());        
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
            user.setAvatar(null);
            return;
        }
        
        cropAndScale();

        DBFile avatarDBfile = new DBFile();
        avatarDBfile.setContentType(FileMimeTypeChecker.getFileMimeType(copied));
        avatarDBfile.setTitle("avatar-" + user.getLogin() + "." + FilenameUtils.getExtension(uploadedAvatar.getFileName()));
        avatarDBfile.setLastModificationTime(new Timestamp(System.currentTimeMillis()));
        avatarDBfile.setData(FileUtils.readFileToByteArray(copied));
        avatarDBfile.setUser(user);
        avatarDBfile.setDirectory(fileDirectoryService.getUserPicturesDirectory(user));
        fileService.saveAndGetId(avatarDBfile);
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
    
    void onSubmit(){
        isBlockSet = false;
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
        if (copied == null) {
            return null;
        }
        return uploadStore.getUriUploadedFile(copied.getName());
    }

    public String[] getSelectModel() {
        return TimeZoneUtil.getTimeZonesStrings();
    }

    @Override
    public String getPageTitle() {
        return Messages.getMessage("registration", getLocale());
    }   
}
