package ua.cn.stu.oop.horus.core.domain.user.group.air;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.user.group.*;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.user.pilot.Pilot;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;
import ua.cn.stu.oop.horus.core.domain.type.TypeCarrier;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.territory.Airfield;

/**
 *
 * @author alex
 */
public class AirGroup
        extends GenericDomain
        implements StringTitleCarrier,
        TypeCarrier<AirGroupType>,
        GroupLinkCarrier {

    private String title;
    private String titleShort;
    private String websiteURL;
    private GroupLink groupLink;
    private AirGroupType type;
    private AirForce airForce;
    private AirGroupSpecialization specialization;
    private Airfield homeBase;
    private AirGroupStatus status;
    private Pilot leader;
    private Pilot leaderDeputy;
    private Timestamp creationRealDate;
    private Timestamp creationGameDate;
    private boolean userDefined;
    private DBFile emblemImage;

    public Timestamp getCreationGameDate() {
        return creationGameDate;
    }

    public void setCreationGameDate(Timestamp creationGameDate) {
        this.creationGameDate = creationGameDate;
    }

    public Timestamp getCreationRealDate() {
        return creationRealDate;
    }

    public void setCreationRealDate(Timestamp creationRealDate) {
        this.creationRealDate = creationRealDate;
    }

    public DBFile getEmblemImage() {
        return emblemImage;
    }

    public void setEmblemImage(DBFile emblemImage) {
        this.emblemImage = emblemImage;
    }

    public Airfield getHomeBase() {
        return homeBase;
    }

    public void setHomeBase(Airfield homeBase) {
        this.homeBase = homeBase;
    }

    public Pilot getLeader() {
        return leader;
    }

    public void setLeader(Pilot leader) {
        this.leader = leader;
    }

    public Pilot getLeaderDeputy() {
        return leaderDeputy;
    }

    public void setLeaderDeputy(Pilot leaderDeputy) {
        this.leaderDeputy = leaderDeputy;
    }

    public AirGroupSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(AirGroupSpecialization specialization) {
        this.specialization = specialization;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

    public AirForce getAirForce() {
        return airForce;
    }

    public void setAirForce(AirForce airForce) {
        this.airForce = airForce;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    @Override
    public AirGroupType getType() {
        return type;
    }

    @Override
    public void setType(AirGroupType type) {
        this.type = type;
    }

    @Override
    public GroupLink getGroupLink() {
        return groupLink;
    }

    @Override
    public void setGroupLink(GroupLink groupLink) {
        this.groupLink = groupLink;
    }

    public boolean isUserDefined() {
        return userDefined;
    }

    public void setUserDefined(boolean userDefined) {
        this.userDefined = userDefined;
    }

    public AirGroupStatus getStatus() {
        return status;
    }

    public void setStatus(AirGroupStatus status) {
        this.status = status;
    }
}
