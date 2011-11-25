package ua.cn.stu.oop.horus.core.domain.text;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class LocalizedData extends GenericDomain {

    private LocalizedTitle localizedTitle;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalizedTitle getLocalizedTitle() {
        return localizedTitle;
    }

    public void setLocalizedTitle(LocalizedTitle localizedTitle) {
        this.localizedTitle = localizedTitle;
    }
}
