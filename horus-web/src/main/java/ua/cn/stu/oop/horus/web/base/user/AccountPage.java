package ua.cn.stu.oop.horus.web.base.user;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.file.DBFileDirectoryService;
import ua.cn.stu.oop.horus.core.service.file.DBFileService;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.pages.store.UploadStore;
import ua.cn.stu.oop.horus.web.util.Messages;
import ua.cn.stu.oop.horus.web.util.file.FileMimeTypeChecker;
import ua.cn.stu.oop.horus.web.util.image.ImageInFileUtil;
import ua.cn.stu.oop.horus.web.util.pages.MessagePageData;
import ua.cn.stu.oop.horus.web.util.time.TimeZoneUtil;

/**
 *
 * @author alex
 */
@Import(library = "context:js/jquery.imgareaselect.js",
stylesheet = "context:css/imgareaselect-default.css")
public abstract class AccountPage extends GenericPage{

    private User user;
    
    private String login;
    private String password;
    private String passwordConfirm;
    private String email;
    
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
    private Request request;
    
    @InjectPage
    private Message messagePage;
    
    @InjectPage
    private UploadStore uploadStore;
    
    @SessionState
    private MessagePageData messageData;
    
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

    @Component(id = "avaZone")
    private Zone avaZone;
    
    @Persist
    private UploadedFile uploadedAvatar;
    
    @Persist
    private File copied;
    
    protected void onActivate(AvailableLocale lang, String timeZone) {
        this.lang = lang;
        this.timeZone = timeZone;
    }
    
    @OnEvent(component = "uploadAvatar", value = JQueryEventConstants.AJAX_UPLOAD)
    public Object onUploadAvatar(UploadedFile uploadedFile) throws IOException {
        uploadedAvatar = uploadedFile;

        copied = new File(ConfigContainer.CONFIG.TMP.getPathToTemporary(uploadedAvatar.getFileName()));
        uploadedAvatar.write(copied);

        return getAvaZone();
    }
    
    public Object onActionFromAvatarCancel() {
        copied = null;
        uploadedAvatar = null;
        return getAvaZone();
    }
    
    protected void setUserAvatar() throws Exception {
        
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
    
    public JSONObject getParams() {
        AvailableLocale aLoc = getLocale();
        JSONObject uploadMessages = new JSONObject()
                .put("typeError", Messages.getMessage("upload.extension.error", aLoc))
                .put("sizeError", Messages.getMessage("upload.size.error", aLoc))
                .put("minSizeError", Messages.getMessage("upload.size.error.min", aLoc))
                .put("emptyError", Messages.getMessage("upload.empty.error", aLoc))
                .put("onLeave", Messages.getMessage("upload.onLeave", aLoc))
                .put("uploadLabel", Messages.getMessage("upload", aLoc))
                .put("dropAreaLabel", Messages.getMessage("upload.dropArea.label", aLoc))
                .put("cancelLabel", Messages.getMessage("cancel", aLoc))
                .put("failedLabel", Messages.getMessage("failure", aLoc));

        JSONObject parameter = new JSONObject().put("messages", uploadMessages);

        return parameter;
    }
    
    protected Object getAvaZone() {
        return request.isXHR() ? new MultiZoneUpdate("avaZone", avaZone.getBody()) : null;
    }
    
    public TextField getEmailField() {
        return emailField;
    }

    public TextField getLoginField() {
        return loginField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public AvailableLocale getLang() {
        if (lang == null) {
            setLang(getLocale());
        }
        return lang;
    }

    public void setLang(AvailableLocale lang) {
        this.lang = lang;
    }

    public String getTimeZone() {
        if (timeZone == null) {
            setTimeZone(TimeZoneUtil.toString(TimeZoneUtil.TIMEZONE_DEFAULT));
        }
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    
    public String[] getSelectModel() {
        return TimeZoneUtil.getTimeZonesStrings();
    }
    
    public MessagePageData getMessageData() {
        return messageData;
    }

    public Message getMessagePage() {
        return messagePage;
    }

    public Request getRequest() {
        return request;
    }   

    public DBFileDirectoryService getFileDirectoryService() {
        return fileDirectoryService;
    }

    public DBFileService getFileService() {
        return fileService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }

    public File getCopied() {
        return copied;
    }
    
    public boolean getIsAvatarUploaded(){
        return (copied!=null);
    }
    
    public abstract String getAvatarUri();
    
    protected String getUploadedAvatarUri() {
        File fAva = getCopied();
        if (fAva == null) {
            return null;
        }
        
        return uploadStore.getUriUploadedFile(fAva.getName());
    }
}
