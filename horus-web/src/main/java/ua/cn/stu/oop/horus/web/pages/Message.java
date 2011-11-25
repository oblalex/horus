package ua.cn.stu.oop.horus.web.pages;

import ua.cn.stu.oop.horus.web.util.pages.MessagePageData;
import org.apache.tapestry5.annotations.SessionState;
import ua.cn.stu.oop.horus.web.util.Messages;

/**
 *
 * @author alex
 */
public class Message {

    @SessionState
    private MessagePageData messageData;

    public MessagePageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessagePageData messageData) {
        this.messageData = messageData;
    }

    
    public String getPageTitle() {
        return Messages.getMessage("message", messageData.getLocale()) + " - " + messageData.getPageTitleTail();
    }
    
    void afterRender() {
        messageData = null;
    }
}
