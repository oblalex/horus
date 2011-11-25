package ua.cn.stu.oop.horus.core.domain.text.report;

import java.security.Timestamp;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public class LocalizedChanges
        extends GenericDomain
        implements StringTitleCarrier {

    private AvailableLocale locale;
    private String title;
    private String body;
    private Timestamp publicationRealTime;

    public AvailableLocale getLocale() {
        return locale;
    }

    public void setLocale(AvailableLocale locale) {
        this.locale = locale;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getPublicationRealTime() {
        return publicationRealTime;
    }

    public void setPublicationRealTime(Timestamp publicationRealTime) {
        this.publicationRealTime = publicationRealTime;
    }
}
