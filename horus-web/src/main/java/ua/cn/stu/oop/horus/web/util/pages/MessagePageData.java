package ua.cn.stu.oop.horus.web.util.pages;

import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public class MessagePageData {

    private AvailableLocale locale;
    private String pageTitleTail;
    private String htmlMessage;
    private int autoRedirectForwardTimeoutSec;
    private MessageType type;
    private String nextPageURL;
    private boolean canGoForward;
    private boolean canGoBackward;

    public enum MessageType {

        INFO,
        SUCCESS,
        WARNING,
        ERROR
    }

    public boolean getCanGoBackward() {
        return canGoBackward;
    }

    public void setCanGoBackward(boolean canGoBackward) {
        this.canGoBackward = canGoBackward;
    }

    public boolean getCanGoForward() {
        return canGoForward;
    }

    public void setCanGoForward(boolean canGoForward) {
        this.canGoForward = canGoForward;
    }

    public String getNextPageURL() {
        return nextPageURL;
    }

    public void setNextPageURL(String nextPageURL) {
        this.nextPageURL = nextPageURL;
    }

    public int getAutoRedirectForwardTimeoutSec() {
        return autoRedirectForwardTimeoutSec;
    }

    public void setAutoRedirectForwardTimeoutSec(int autoRedirectForwardTimeoutSec) {
        this.autoRedirectForwardTimeoutSec = autoRedirectForwardTimeoutSec;
    }

    public String getHtmlMessage() {
        return htmlMessage;
    }

    public void setHtmlMessage(String htmlMessage) {
        this.htmlMessage = htmlMessage;
    }

    public AvailableLocale getLocale() {
        return locale;
    }

    public void setLocale(AvailableLocale locale) {
        this.locale = locale;
    }

    public String getPageTitleTail() {
        return pageTitleTail;
    }

    public void setPageTitleTail(String pageTitleTail) {
        this.pageTitleTail = pageTitleTail;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
    
    public boolean getIsAutoRedirectNeeded(){
        return canGoForward && (autoRedirectForwardTimeoutSec>0);
    }
}
