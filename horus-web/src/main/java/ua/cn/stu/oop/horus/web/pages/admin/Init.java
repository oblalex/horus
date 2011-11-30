package ua.cn.stu.oop.horus.web.pages.admin;

import ua.cn.stu.oop.horus.web.util.pages.RawHtmlTags;
import ua.cn.stu.oop.horus.web.util.pages.MessagePageData;
import java.sql.Timestamp;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.user.*;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
public class Init extends GenericPage{
    
    @Inject
    @Autowired
    private UserService userService;
    
    @Inject
    @Autowired
    private UserAdminService adminService;
    
    @SessionState
    private MessagePageData messageData;
    
    private String login;
    private String pswd;
    private User user;
    private StringBuilder htmlMsgBuilder;
    private static final short timezone = 0;
    
    public Object onActivate() {        
        htmlMsgBuilder = new StringBuilder();
        
        if (adminService.noAdminExists()){
            onAdminNotExists();
        } else {
            onAdminExists();
        }
        
        finishMessageDataPreparations();
        return Message.class;
    }
    
    private void onAdminExists(){
        messageData.setType(MessagePageData.MessageType.INFO);
        htmlMsgBuilder.append(Messages.getMessage("admin.init.already", getLocale()));
    }
    
    private void onAdminNotExists(){
        login = Constants.getConstant("admin.login");
        user = userService.getUserOrNullByLogin(login);
        if (user==null){
            createNewAdminUser();
        } else {
            addUserToAdmins();
        }
    }
    
    private void createNewAdminUser(){
        user = new User();
        pswd = Constants.getConstant("admin.password");
        
        ByteSource bs = EncodingUtil.getRandomSaltSource();
        AvailableLocale aLoc = getLocale();
        
        user.setSalt(bs.getBytes());
        
        user.setLogin(login);        
        user.setHashedPassword(new Sha1Hash(pswd, bs).toString());
        user.setPreferredLocale(aLoc);
        user.setTimeZoneUTC(timezone);
        user.setEmail(ConfigContainer.CONFIG.MAIL.username);
        user.setEmailConfirmed(true);
        user.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        
        userService.saveAndGetId(user);
        
        String langName = Messages.getMessage(getLocale().name(), aLoc);
        
        htmlMsgBuilder.append(Messages.getMessage("usr.created", aLoc));
        htmlMsgBuilder.append(RawHtmlTags.BR).append(RawHtmlTags.BR);
        
        appendKeyAndValue("usr.login", login);
        appendKeyAndValue("usr.pswd", pswd);
        appendKeyAndValue("usr.email", user.getEmail());
        appendKeyAndValue("timeZone", String.format("%+d", timezone));
        appendKeyAndValue("lang", langName);
                        
        htmlMsgBuilder.append(RawHtmlTags.BR);
        
        addUserToAdmins();
    }
    
    private void appendKeyAndValue(String key, String value){
        htmlMsgBuilder.append(Messages.getMessage(key, getLocale()));
        htmlMsgBuilder.append(": ");
        htmlMsgBuilder.append(value).append(".").append(RawHtmlTags.BR);
    }
    
    private void addUserToAdmins(){
        UserAdmin admin = new UserAdmin();
        AvailableLocale aLoc = getLocale();
        
        admin.setUser(user);
        admin.setComment(Messages.getMessage("admin.usr.main", aLoc));
        
        adminService.saveAndGetId(admin);
        htmlMsgBuilder.append(Messages.getMessage("admin.init.success", aLoc));
        messageData.setType(MessagePageData.MessageType.SUCCESS);
    }
    
    private void finishMessageDataPreparations() {
        messageData.setPageTitleTail(Messages.getMessage("admin.init", getLocale()));
        messageData.setLocale(getLocale());
        messageData.setCanGoBackward(false);
        messageData.setCanGoForward(false);
        messageData.setHtmlMessage(htmlMsgBuilder.toString());
    }

    @Override
    public String getPageTitle() {
        return this.getClass().getSimpleName();
    }
}
