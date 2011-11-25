package ua.cn.stu.oop.horus.core.domain.servercommander.config;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.text.TitleLinkCarrier;

/**
 *
 * @author alex
 */
public class ConfigParameter
        extends GenericDomain
        implements TitleLinkCarrier {

    private String codeName;
    private TitleLink titleLink;

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }
}
