package ua.cn.stu.oop.horus.web.pages.user;

import java.util.Locale;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.tynamo.security.services.SecurityService;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.user.UserService;
import ua.cn.stu.oop.horus.web.components.Layout;
import ua.cn.stu.oop.horus.web.pages.Index;
import ua.cn.stu.oop.horus.web.util.Messages;

/**
 *
 * @author alex
 */
public class Login {

    @Inject
    @Autowired
    private UserService userService;
    @Inject
    private PersistentLocale persistentLocale;
    private AvailableLocale locale = Layout.getLocaleFromPersistent(persistentLocale);
    @Inject
    private ComponentResources componentResources;
    @Inject
    private SecurityService securityService;
    @Inject
    private Request request;
    @Property
    private String login;
    @Property
    private String password;
    @Property
    private boolean rememberMe;
    @Component(id = "login")
    private TextField loginField;
    @Component(id = "password")
    private PasswordField passwordField;
    @Component
    private Form loginForm;
    @Component(id = "formZone")
    private Zone formZone;
    private User user;

    Object onActivate() {
        if (securityService.isGuest()) {
            return null;
        } else {
            return Index.class;
        }
    }

    void onValidate() {
        loginForm.clearErrors();
        checkLogin();
        checkPassword();
        if (loginForm.getHasErrors() == false) {
            tryLogin();
        }
    }

    Object onSuccess() {
        checkLocale();
        componentResources.discardPersistentFieldChanges();
        return Index.class;
    }
    
    Object onFailure() {
        return request.isXHR() ? formZone.getBody() : null;
    }
    
    private void checkLocale(){
        if (locale.equals(user.getPreferredLocale())==false){
            persistentLocale.set(new Locale(user.getPreferredLocale().name()));
        }
    }

    private void checkLogin() {
        if (login == null) {
            loginForm.recordError(loginField, Messages.getMessage("usr.login.undef", locale));
        }
    }

    private void checkPassword() {
        if (password == null) {
            loginForm.recordError(passwordField, Messages.getMessage("usr.pswd.undef", locale));
        }
    }

    private void tryLogin() {

        user = userService.getUserOrNullByLogin(login);

        if (user == null) {
            loginForm.recordError(Messages.getMessage("usr.not.found", locale));
            return;
        } else if (user.isEmailConfirmed() == false) {
            loginForm.recordError(Messages.getMessage("usr.account.activated.not", locale));
            return;
        }

        Subject currentUser = securityService.getSubject();

        if (currentUser == null) {
            throw new IllegalStateException("Subject can`t be null");
        }      
        
        UsernamePasswordToken token = new UsernamePasswordToken(login, password);
        token.setRememberMe(rememberMe);

        try {
            currentUser.login(token);
        } catch (IncorrectCredentialsException e) {
            loginForm.recordError(passwordField, Messages.getMessage("usr.pswd.wrong", locale));
        } catch (LockedAccountException e) {
            loginForm.recordError(Messages.getMessage("usr.account.locked", locale));
        }
    }

    
    public String getPageTitle() {
        return Messages.getMessage("login", locale);
    }
}
