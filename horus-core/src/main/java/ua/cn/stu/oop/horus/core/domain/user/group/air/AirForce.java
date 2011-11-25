package ua.cn.stu.oop.horus.core.domain.user.group.air;

import ua.cn.stu.oop.horus.core.domain.*;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.domain.territory.Country;
import ua.cn.stu.oop.horus.core.domain.user.pilot.rank.Rank;

/**
 *
 * @author alex
 */
public class AirForce
        extends GenericDomain
        implements TitleLinkCarrier {

    private Country country;
    private Belligerent belligerent;
    private TitleLink titleLink;
    private Rank initialPilotRank;
    private AirGroupType initialAirGroupType;
    private AirGroupStatus initialAirGroupStatus;
    private DBFile emblemImage;
    private DBFile makringsImage;

    public DBFile getEmblemImage() {
        return emblemImage;
    }

    public void setEmblemImage(DBFile emblemImage) {
        this.emblemImage = emblemImage;
    }

    public DBFile getMakringsImage() {
        return makringsImage;
    }

    public void setMakringsImage(DBFile makringsImage) {
        this.makringsImage = makringsImage;
    }

    public AirGroupStatus getInitialAirGroupStatus() {
        return initialAirGroupStatus;
    }

    public void setInitialAirGroupStatus(AirGroupStatus initialAirGroupStatus) {
        this.initialAirGroupStatus = initialAirGroupStatus;
    }

    public Rank getInitialPilotRank() {
        return initialPilotRank;
    }

    public void setInitialPilotRank(Rank initialPilotRank) {
        this.initialPilotRank = initialPilotRank;
    }

    public Belligerent getBelligerent() {
        return belligerent;
    }

    public void setBelligerent(Belligerent belligerent) {
        this.belligerent = belligerent;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public AirGroupType getInitialAirGroupType() {
        return initialAirGroupType;
    }

    public void setInitialAirGroupType(AirGroupType initialAirGroupType) {
        this.initialAirGroupType = initialAirGroupType;
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
