package ua.cn.stu.oop.horus.core.domain.user.pilot;

import ua.cn.stu.oop.horus.core.domain.user.pilot.rank.Rank;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.territory.Airfield;
import ua.cn.stu.oop.horus.core.domain.user.UserCarrier;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroupCarrier;

/**
 *
 * @author alex
 */
public class Pilot
        extends GenericDomain
        implements AirGroupCarrier,
                    UserCarrier{

    private User user;
    private Rank rank;
    private AirGroup airGroup;
    private Airfield homeBase;
    private Airfield currentBase;
    private String callsign;
    private String aircraftTailNumber;
    private long score;
    private long lifeNumber;
    private long timeToIdleMilliseconds;
    private long flyTimeMilliseconds;
    private float experience;
    private boolean allowedToEnterGameServers;

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public AirGroup getAirGroup() {
        return airGroup;
    }

    @Override
    public void setAirGroup(AirGroup airGroup) {
        this.airGroup = airGroup;
    }

    public String getAircraftTailNumber() {
        return aircraftTailNumber;
    }

    public void setAircraftTailNumber(String aircraftTailNumber) {
        this.aircraftTailNumber = aircraftTailNumber;
    }

    public boolean isAllowedToEnterGameServers() {
        return allowedToEnterGameServers;
    }

    public void setAllowedToEnterGameServers(boolean allowedToEnterGameServers) {
        this.allowedToEnterGameServers = allowedToEnterGameServers;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public Airfield getCurrentBase() {
        return currentBase;
    }

    public void setCurrentBase(Airfield currentBase) {
        this.currentBase = currentBase;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public long getFlyTimeMilliseconds() {
        return flyTimeMilliseconds;
    }

    public void setFlyTimeMilliseconds(long flyTimeMilliseconds) {
        this.flyTimeMilliseconds = flyTimeMilliseconds;
    }

    public Airfield getHomeBase() {
        return homeBase;
    }

    public void setHomeBase(Airfield homeBase) {
        this.homeBase = homeBase;
    }

    public long getLifeNumber() {
        return lifeNumber;
    }

    public void setLifeNumber(long lifeNumber) {
        this.lifeNumber = lifeNumber;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getTimeToIdleMilliseconds() {
        return timeToIdleMilliseconds;
    }

    public void setTimeToIdleMilliseconds(long timeToIdleMilliseconds) {
        this.timeToIdleMilliseconds = timeToIdleMilliseconds;
    }
}
