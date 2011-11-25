package ua.cn.stu.oop.horus.web.pages.user;

import ua.cn.stu.oop.horus.web.util.pages.MessagePageData;
import javax.servlet.http.HttpServletRequest;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.components.Layout;
import ua.cn.stu.oop.horus.web.util.mail.*;
import ua.cn.stu.oop.horus.web.pages.Message;
import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
public class MailConfirm {

    @Inject
    @Autowired
    private UserService userService;
    
    @SessionState
    private MessagePageData messageData;
    
    @Inject
    private HttpServletRequest request;
    
    @Inject
    private PersistentLocale persistentLocale;    
    private AvailableLocale locale= Layout.getLocaleFromPersistent(persistentLocale);
    
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
        if (login == null || login.isEmpty()) {
            htmlMsgBuilder.append(Messages.getMessage("usr.login.undef", locale));
        } else {
            user = userService.getUserOrNullByLogin(login);
            if (user == null) {
                htmlMsgBuilder.append(Messages.getMessage("usr.not.found", locale));
            }
        }
    }

    private void tryConfirm() {
        if (key == null || key.isEmpty()) {
            htmlMsgBuilder.append(Messages.getMessage("key.undef", locale));
        } else {
            if (user != null) {
                activatedAlready = user.isEmailConfirmed();
                if (activatedAlready) { 
                    htmlMsgBuilder.append(Messages.getMessage("usr.account.activated.already.msg", locale));
                } else {
                    String realKey = EncodingUtil.encodeStringUsingSaltBytes(user.getEmail(), user.getSalt());
                    if (realKey.equals(key)) {
                        user.setEmailConfirmed(true);
                        userService.updateEntity(user);
                        htmlMsgBuilder.append(Messages.getMessage("usr.account.activated.msg", locale));
                    } else {
                        htmlMsgBuilder.append(Messages.getMessage("usr.email.confirm.key.not.match", locale));
                    }
                }
            }
        }
    }

    private void setMessageTypeAndTitleTail() {
        if (user == null || user.isEmailConfirmed() == false) {
            messageData.setType(MessagePageData.MessageType.ERROR);
            messageData.setPageTitleTail(Messages.getMessage("usr.email.confirm.failure", locale));
        } else if (activatedAlready) {
            messageData.setType(MessagePageData.MessageType.INFO);
            messageData.setPageTitleTail(Messages.getMessage("usr.email.confirm", locale));
        } else {
            messageData.setType(MessagePageData.MessageType.SUCCESS);
            messageData.setPageTitleTail(Messages.getMessage("usr.email.confirm.success", locale));
        }
    }

    private void finishMessageDataPreparations() {
        messageData.setLocale(locale);
        messageData.setCanGoBackward(false);
        messageData.setCanGoForward(false);
        messageData.setHtmlMessage(htmlMsgBuilder.toString());
    }
}
