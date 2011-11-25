package ua.cn.stu.oop.horus.web.util.mail;

import ua.cn.stu.oop.horus.web.util.pages.RawHtmlTags;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.cn.stu.oop.horus.core.domain.text.LocalizedTitle;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.service.text.LocalizedTitleService;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
public class RegistrationNotifyMail extends GenericMail {

    private ApplicationContext ctx;
    public LocalizedTitleService localizedTitleService;
    
    public static final String PARAM_LOGIN  = "login";
    public static final String PARAM_KEY    = "key";
    
    private User user;
    private String rawPassword;
    private String projName;
    private String siteRootURL;    

    public RegistrationNotifyMail(
            User user,
            String rawPassword,
            String siteRootURL) throws AddressException {

        this.recipientAddress = new InternetAddress(user.getEmail());
        this.locale = user.getPreferredLocale();
        this.user = user;
        this.rawPassword = rawPassword;
        this.siteRootURL = siteRootURL;
        
        ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/coreContext.xml");
        localizedTitleService = ctx.getBean(LocalizedTitleService.class);
        LocalizedTitle localizedTitle = 
                localizedTitleService.
                getTitleWithDefaultGrammarByTitleLinkIdAndLocale(
                    ConfigContainer.CONFIG.GENERAL.projectNameTitleLinkId, locale);
        this.projName = localizedTitle.getTitle();
    }

    @Override
    public void fillMimeMessage(MimeMessage mm) throws MessagingException {
        fillRecipient(mm);
        fillSubject(mm);
        fillBody(mm);
    }

    private void fillRecipient(MimeMessage mm) throws MessagingException {
        mm.setRecipient(RecipientType.TO, recipientAddress);
    }

    private void fillSubject(MimeMessage mm) throws MessagingException {
        String subjectKey = "mail.newreg.subj";
        String subject = Messages.getMessageParameterized(subjectKey, locale, projName);
        mm.setSubject(subject);
    }

    private void fillBody(MimeMessage mm) throws MessagingException {
        
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(buildHtmlMimeBody());        
        mm.setContent(mp);
    }

    private MimeBodyPart buildHtmlMimeBody() throws MessagingException {
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(buildHtmlPlainBody(), "text/html; charset=UTF-8");
        return mbp;
    }

    private String buildHtmlPlainBody() {
        StringBuilder sb = new StringBuilder();

        String delim = RawHtmlTags.BR;
        
        sb.append("<html><body>");
        
        String greeting = Messages.getMessageParameterized("usr.greeting", locale, user.getLogin());
        sb.append(greeting).append(delim).append(delim);

        String welcome = Messages.getMessageParameterized("mail.newreg.welcome", locale, projName);
        sb.append(welcome).append(delim);

        String dontForget = Messages.getMessage("mail.newreg.dontforget", locale);
        sb.append(dontForget).append(delim).append(delim);

        String yourLogin = Messages.getMessage("usr.your.login", locale);
        sb.append(yourLogin).append(" : ").append(user.getLogin()).append(".").append(delim);

        String yourPswd = Messages.getMessage("usr.your.pswd", locale);
        sb.append(yourPswd).append(" : ").append(rawPassword).append(".").append(delim).append(delim);
        
        String goToLink = Messages.getMessage("go.to.link", locale);
        String formedLink = RawHtmlTags.buildAnchor(getMailConfirmURL(), goToLink);        
        String toActivateAccount = Messages.getMessage("to.activate.account", locale);
        
        sb.append(toActivateAccount).append(" ").append(formedLink).append(".").append(delim).append(delim);
        
        sb.append(RawHtmlTags.HR).append(delim);
        sb.append(getAutoMessageAbout());
        
        sb.append("</body></html>");
        
        return sb.toString();
    }
    
    private String getMailConfirmURL(){
        StringBuilder sb = new StringBuilder();
        
        sb.append(siteRootURL);
        sb.append(locale.name());
        sb.append("/");
        sb.append("mailconfirm");
        sb.append("/?");
        sb.append(PARAM_LOGIN);
        sb.append("=");
        sb.append(user.getLogin());
        sb.append("&");
        sb.append(PARAM_KEY);
        sb.append("=");
        
        String key = EncodingUtil.encodeStringUsingSaltBytes(user.getEmail(), user.getSalt());
        sb.append(key);
        
        return sb.toString();
    }
        
}
