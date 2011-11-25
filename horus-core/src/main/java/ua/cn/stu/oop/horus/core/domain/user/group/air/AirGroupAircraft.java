package ua.cn.stu.oop.horus.core.domain.user.group.air;

import com.vividsolutions.jts.geom.Point;
import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.object.Aircraft;
import ua.cn.stu.oop.horus.core.domain.territory.Battleground;
import ua.cn.stu.oop.horus.core.domain.user.pilot.Pilot;

/**
 *
 * @author alex
 */
public class AirGroupAircraft
        extends GenericDomain
        implements AirGroupCarrier {

    private AirGroup airGroup;
    private Pilot pilot;
    private Aircraft aircraft;
    private Battleground battleground;
    private Point point;
    private short damagesCount;
    private Timestamp gameDateToIdle;

    public short getDamagesCount() {
        return damagesCount;
    }

    public Battleground getBattleground() {
        return battleground;
    }

    public void setBattleground(Battleground battleground) {
        this.battleground = battleground;
    }

    public void setDamagesCount(short damagesCount) {
        this.damagesCount = damagesCount;
    }

    public Timestamp getGameDateToIdle() {
        return gameDateToIdle;
    }

    public void setGameDateToIdle(Timestamp gameDateToIdle) {
        this.gameDateToIdle = gameDateToIdle;
    }

    @Override
    public AirGroup getAirGroup() {
        return airGroup;
    }

    @Override
    public void setAirGroup(AirGroup airGroup) {
        this.airGroup = airGroup;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
