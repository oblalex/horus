package ua.cn.stu.oop.horus.web.util.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.*;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;

/**
 *
 * @author alex
 */
public class MailService {

    private JavaMailSenderImpl mailSender;

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = (JavaMailSenderImpl) mailSender;
        setSenderParameters();
    }

    private void setSenderParameters() {
        try {
            mailSender.setUsername(ConfigContainer.CONFIG.MAIL.username);
            mailSender.setPassword(ConfigContainer.CONFIG.MAIL.password);
            mailSender.setHost(ConfigContainer.CONFIG.MAIL.host);
            mailSender.setPort(ConfigContainer.CONFIG.MAIL.port);
        } catch (Exception e){
        }
    }

    public void sendMail(final GenericMail mail) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        mail.fillMimeMessage(message);
        mailSender.send(message);
    }
}
