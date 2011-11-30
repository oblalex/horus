package ua.cn.stu.oop.horus.web.base.user;

import java.io.File;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.json.JSONObject;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.util.Messages;
import ua.cn.stu.oop.horus.web.util.pages.MessagePageData;
import ua.cn.stu.oop.horus.web.util.time.TimeZoneUtil;

/**
 *
 * @author alex
 */
@Import(library = "context:js/jquery.imgareaselect.js",
stylesheet = "context:css/imgareaselect-default.css")
public abstract class AccountPage extends GenericPage{

    private String login;
    private String password;
    private String passwordConfirm;
    private String email;
    
    @InjectPage
    private Message messagePage;
    
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

    @Persist
    private File copied;
    
    protected void onActivate(AvailableLocale lang, String timeZone) {
        this.lang = lang;
        this.timeZone = timeZone;
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

    public MessagePageData getMessageData() {
        return messageData;
    }

    public Message getMessagePage() {
        return messagePage;
    }
}
