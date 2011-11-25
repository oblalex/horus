package ua.cn.stu.oop.horus.core.domain.user.group.air;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.text.TitleLinkCarrier;

/**
 *
 * @author alex
 */
public class AirGroupSpecialization
        extends GenericDomain
        implements TitleLinkCarrier {

    private TitleLink titleLink;

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }
}
