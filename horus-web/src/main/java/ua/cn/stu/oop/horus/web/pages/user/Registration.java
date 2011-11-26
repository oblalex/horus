package ua.cn.stu.oop.horus.web.pages.user;

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
import org.apache.tapestry5.json.JSONObject;
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
import ua.cn.stu.oop.horus.web.components.Layout;
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
@Import(library = "context:js/jquery.imgareaselect.js",
stylesheet = "context:css/imgareaselect-default.css")
public class Registration {

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
    private Message messagePage;
    
    @InjectPage
    private UploadStore uploadStore;
    
    @SessionState
    private MessagePageData messageData;
    
    @Inject
    private ComponentResources componentResources;
    
    @Inject
    private Request request;
    
    @Inject
    private HttpServletRequest httpRequest;
    
    @Inject
    private PersistentLocale persistentLocale;
    
    private AvailableLocale locale = Layout.getLocaleFromPersistent(persistentLocale);
    
    @Property
    private String login;
    
    @Property
    private String password;
    
    @Property
    private String passwordConfirm;
    
    @Property
    private String email;
    
    @Persist
    private AvailableLocale lang;
    
    @Persist
    private String timeZone;
    
    @Component(id = "login")
    private TextField loginField;
    
    @Component(id = "password")
    private PasswordField passwordField;

    @Component(id = "email")
    private TextField emailField;
    
    @Component
    private Form registrationForm;
    
    @Component(id = "formZone")
    private Zone formZone;
    
    @Component(id = "avaZone")
    private Zone avaZone;
    
    @Persist
    private UploadedFile uploadedAvatar;
    
    @Persist
    private String avatarFileName;
    
    @Persist
    private File copied;
    
    private boolean isBlockSet = false;

    void onActivate(AvailableLocale lang, String timeZone) {
        this.lang = lang;
        this.timeZone = timeZone;
    }
    
    @OnEvent(component = "uploadAvatar", value = JQueryEventConstants.AJAX_UPLOAD)
    Object onUploadAvatar(UploadedFile uploadedFile) throws IOException {
        uploadedAvatar = uploadedFile;

        copied = new File(ConfigContainer.CONFIG.TMP.getPathToTemporary(uploadedAvatar.getFileName()));
        uploadedAvatar.write(copied);

        avatarFileName = copied.getName();
        return getAvaZone();
    }
    
    Object onActionFromAvatarCancel() {
        copied = null;
        uploadedAvatar = null;
        avatarFileName = null;
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
                loginField,
                loginValidator.validateAndGetErrorMessageOrNull(
                    locale, login));
    }

    private void validatePassword() {
        registrationForm.recordError(
                passwordField,
                passwordValidator.validateAndGetErrorMessageOrNull(
                    locale, password, passwordConfirm));
    }

    private void validateEmail() {
        registrationForm.recordError(
                emailField,
                emailValidator.validateAndGetErrorMessageOrNull(
                    locale, email));        
    }    
    
    private void trySendRegistrationNotificationMail(){               
        try {
            mailService.sendMail(new RegistrationNotifyMail(
                                 user,
                                 password,
                                 HttpRequestHelper.getContextRootUrl(httpRequest)));
        } catch (MessagingException ex) {
            onMailSendFailure();
        }
    }

    private void onMailSendFailure() {
        registrationForm.recordError(Messages.getMessage("registration.failure.msg", lang));        
    }
    
    Object onSuccess(){
        finishUserCreation();

        componentResources.discardPersistentFieldChanges();
        
        messageData.setType(MessagePageData.MessageType.SUCCESS);
        messageData.setPageTitleTail(Messages.getMessage("registration.success", lang));
        messageData.setHtmlMessage(Messages.getMessage("registration.success.msg", lang));        
        messageData.setLocale(lang);
        messageData.setCanGoForward(false);
        messageData.setCanGoBackward(false);
        messagePage.setMessageData(messageData);
        
        return messagePage;
    }

    private void prepareUser(){
        user = new User();

        user.setLogin(login);
        user.setEmail(email);
        user.setEmailConfirmed(false);
        user.setPreferredLocale(lang);
        user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        user.setTimeZoneUTC(TimeZoneUtil.parseTimeZone(timeZone));

        ByteSource bs = EncodingUtil.getRandomSaltSource();
        user.setSalt(bs.getBytes());
        user.setHashedPassword(new Sha1Hash(password, bs).toString());        
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
        if (avatarFileName == null) {
            return null;
        }
        return uploadStore.getUriUploadedFile(avatarFileName);
    }

    public String[] getSelectModel() {
        return TimeZoneUtil.getTimeZonesStrings();
    }

    public String getPageTitle() {
        return Messages.getMessage("registration", locale);
    }

    public AvailableLocale getLang() {
        if (lang == null) {
            lang = locale;
        }
        return lang;
    }

    public void setLang(AvailableLocale lang) {
        this.lang = lang;
    }

    public String getTimeZone() {
        if (timeZone == null) {
            timeZone = TimeZoneUtil.toString(TimeZoneUtil.TIMEZONE_DEFAULT);
        }
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public MessagePageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessagePageData messageData) {
        this.messageData = messageData;
    }

    public JSONObject getParams() {
        JSONObject uploadMessages = new JSONObject()
                .put("typeError", Messages.getMessage("upload.extension.error", locale))
                .put("sizeError", Messages.getMessage("upload.size.error", locale))
                .put("minSizeError", Messages.getMessage("upload.size.error.min", locale))
                .put("emptyError", Messages.getMessage("upload.empty.error", locale))
                .put("onLeave", Messages.getMessage("upload.onLeave", locale))
                .put("uploadLabel", Messages.getMessage("upload", locale))
                .put("dropAreaLabel", Messages.getMessage("upload.dropArea.label", locale))
                .put("cancelLabel", Messages.getMessage("cancel", locale))
                .put("failedLabel", Messages.getMessage("failure", locale));

        JSONObject parameter = new JSONObject().put("messages", uploadMessages);

        return parameter;
    }
}
