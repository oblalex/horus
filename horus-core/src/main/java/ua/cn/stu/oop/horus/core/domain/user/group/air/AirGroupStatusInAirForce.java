package ua.cn.stu.oop.horus.core.domain.user.group.air;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class AirGroupStatusInAirForce
        extends GenericDomain
        implements TitleLinkCarrier {

    private AirGroupStatus status;
    private AirForce airForce;
    private TitleLink titleLink;
    private DBFile emblemImage;

    public DBFile getEmblemImage() {
        return emblemImage;
    }

    public void setEmblemImage(DBFile emblemImage) {
        this.emblemImage = emblemImage;
    }

    public AirForce getAirForce() {
        return airForce;
    }

    public void setAirForce(AirForce airForce) {
        this.airForce = airForce;
    }

    public AirGroupStatus getStatus() {
        return status;
    }

    public void setStatus(AirGroupStatus status) {
        this.status = status;
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
