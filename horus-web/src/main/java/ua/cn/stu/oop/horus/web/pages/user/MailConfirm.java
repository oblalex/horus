package ua.cn.stu.oop.horus.web.pages.user;

import ua.cn.stu.oop.horus.web.util.pages.MessagePageData;
import javax.servlet.http.HttpServletRequest;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.util.mail.*;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
public class MailConfirm extends GenericPage{

    @Inject
    @Autowired
    private UserService userService;
    
    @SessionState
    private MessagePageData messageData;
    
    @Inject
    private HttpServletRequest request;
        
    private boolean activatedAlready;
    
    private User user;
    
    private String login;
    
    private String key;
    
    private StringBuilder htmlMsgBuilder;

    public Object onActivate() {
        activatedAlready = false;
        htmlMsgBuilder = new StringBuilder();
        
        getParametersValuesFromRequest();
        tryGetUserByLogin();
        tryConfirm();
        setMessageTypeAndTitleTail();
        finishMessageDataPreparations();
        return Message.class;
    }

    private void getParametersValuesFromRequest() {
        login = request.getParameter(RegistrationNotifyMail.PARAM_LOGIN);
        key = request.getParameter(RegistrationNotifyMail.PARAM_KEY);
    }

    private void tryGetUserByLogin() {
        
        AvailableLocale aLoc = getLocale();
        
        if (login == null || login.isEmpty()) {
            htmlMsgBuilder.append(WebMessages.getMessage("usr.login.undef", aLoc));
        } else {
            user = userService.getUserOrNullByLogin(login);
            if (user == null) {
                htmlMsgBuilder.append(WebMessages.getMessage("usr.not.found", aLoc));
            }
        }
    }

    private void tryConfirm() {
        AvailableLocale aLoc = getLocale();
        
        if (key == null || key.isEmpty()) {
            htmlMsgBuilder.append(WebMessages.getMessage("key.undef", aLoc));
        } else {
            if (user != null) {
                activatedAlready = user.isEmailConfirmed();
                if (activatedAlready) { 
                    htmlMsgBuilder.append(WebMessages.getMessage("usr.account.activated.already.msg", aLoc));
                } else {
                    String realKey = EncodingUtil.encodeStringUsingSaltBytes(user.getEmail(), user.getSalt());
                    if (realKey.equals(key)) {
                        user.setEmailConfirmed(true);
                        userService.updateEntity(user);
                        htmlMsgBuilder.append(WebMessages.getMessage("usr.account.activated.msg", aLoc));
                    } else {
                        htmlMsgBuilder.append(WebMessages.getMessage("usr.email.confirm.key.not.match", aLoc));
                    }
                }
            }
        }
    }

    private void setMessageTypeAndTitleTail() {
        
        AvailableLocale aLoc = getLocale();
        
        if (user == null || user.isEmailConfirmed() == false) {
            messageData.setType(MessagePageData.MessageType.ERROR);
            messageData.setPageTitleTail(WebMessages.getMessage("usr.email.confirm.failure", aLoc));
        } else if (activatedAlready) {
            messageData.setType(MessagePageData.MessageType.INFO);
            messageData.setPageTitleTail(WebMessages.getMessage("usr.email.confirm", aLoc));
        } else {
            messageData.setType(MessagePageData.MessageType.SUCCESS);
            messageData.setPageTitleTail(WebMessages.getMessage("usr.email.confirm.success", aLoc));
        }
    }

    private void finishMessageDataPreparations() {
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
