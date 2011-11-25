package ua.cn.stu.oop.horus.core.domain.gameflow;

import com.vividsolutions.jts.geom.Point;
import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.loadout.*;
import ua.cn.stu.oop.horus.core.domain.user.pilot.Pilot;

/**
 *
 * @author alex
 */
public class Sortie
        extends GenericDomain
        implements LoadoutCarrier<TechnologyUnitLoadout> {
    
    private Mission mission;
    private Pilot pilot;
    private TechnologyUnitLoadout loadout;
    private short fuelInitialLevelPercentage;
    private long pilotLifeNumber;
    private Point startPoint;
    private Point endPoint;
    private Timestamp startRealTime;
    private Timestamp stopRealTime;
    private Timestamp startGameTime;
    private Timestamp stopGameTime;
    
    public Point getEndPoint() {
        return endPoint;
    }
    
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
    
    public short getFuelInitialLevelPercentage() {
        return fuelInitialLevelPercentage;
    }
    
    public void setFuelInitialLevelPercentage(short fuelInitialLevelPercentage) {
        this.fuelInitialLevelPercentage = fuelInitialLevelPercentage;
    }
    
    @Override
    public TechnologyUnitLoadout getLoadout() {
        return loadout;
    }
    
    @Override
    public void setLoadout(TechnologyUnitLoadout loadout) {
        this.loadout = loadout;
    }
    
    public Mission getMission() {
        return mission;
    }
    
    public void setMission(Mission mission) {
        this.mission = mission;
    }
    
    public Pilot getPilot() {
        return pilot;
    }
    
    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }
    
    public long getPilotLifeNumber() {
        return pilotLifeNumber;
    }
    
    public void setPilotLifeNumber(long pilotLifeNumber) {
        this.pilotLifeNumber = pilotLifeNumber;
    }
    
    public Timestamp getStartGameTime() {
        return startGameTime;
    }
    
    public void setStartGameTime(Timestamp startGameTime) {
        this.startGameTime = startGameTime;
    }
    
    public Point getStartPoint() {
        return startPoint;
    }
    
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }
    
    public Timestamp getStartRealTime() {
        return startRealTime;
    }
    
    public void setStartRealTime(Timestamp startRealTime) {
        this.startRealTime = startRealTime;
    }
    
    public Timestamp getStopGameTime() {
        return stopGameTime;
    }
    
    public void setStopGameTime(Timestamp stopGameTime) {
        this.stopGameTime = stopGameTime;
    }
    
    public Timestamp getStopRealTime() {
        return stopRealTime;
    }
    
    public void setStopRealTime(Timestamp stopRealTime) {
        this.stopRealTime = stopRealTime;
    }
}
