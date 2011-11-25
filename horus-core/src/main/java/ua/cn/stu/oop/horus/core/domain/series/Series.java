package ua.cn.stu.oop.horus.core.domain.series;

import ua.cn.stu.oop.horus.core.domain.territory.Country;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class Series
        extends GenericDomain
        implements TitleLinkCarrier {

    private TitleLink titleLink;
    private Country manufacturer;

    public Country getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Country manufacturer) {
        this.manufacturer = manufacturer;
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
