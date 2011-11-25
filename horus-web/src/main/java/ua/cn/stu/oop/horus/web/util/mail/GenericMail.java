package ua.cn.stu.oop.horus.web.util.mail;

import javax.mail.MessagingException;
import javax.mail.internet.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.Messages;

/**
 *
 * @author alex
 */
abstract public class GenericMail {

    protected InternetAddress recipientAddress;
    protected AvailableLocale locale;

    abstract public void fillMimeMessage(MimeMessage mm) throws MessagingException;
    
    protected String getAutoMessageAbout(){
        return Messages.getMessage("dont.reply.msg", locale);
    }
}
