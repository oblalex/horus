package ua.cn.stu.oop.horus.core.domain.gameflow;

import ua.cn.stu.oop.horus.core.domain.object.GameObject;
import com.vividsolutions.jts.geom.Point;
import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.TypeCarrier;

/**
 *
 * @author alex
 */
public class MissionEvent 
        extends GenericDomain
        implements TypeCarrier<MissionEvent.MissionEventType> {

    private Timestamp eventRealTime;
    private Timestamp eventGameTime;
    private Point point;
    private Mission mission;
    private MissionEventType type;
    private Sortie attackerSortie;
    private Sortie victimSortie;
    private GameObject attackerObject;
    private GameObject victimObject;
    private boolean objectAttacked;
    private boolean objectWasAttacked;
    private boolean AI_Attacked;
    private boolean AI_WasAttacked;

    public enum MissionEventType implements EntityType {

        SPAWN,
        TAKEOFF,
        LANDING,
        LANDING_CRASH,
        LANDING_IN_FIELD,
        DAMAGED_ON_GROUND,
        KILL_AIR,
        KILL_AIR_FOE,
        KILL_AIR_FRIEND,
        KILL_GROUND,
        KILL_GROUND_FOE,
        KILL_GROUND_FRIEND
    }

    public boolean isAI_Attacked() {
        return AI_Attacked;
    }

    public void setAI_Attacked(boolean AI_Attacked) {
        this.AI_Attacked = AI_Attacked;
    }

    public boolean isAI_WasAttacked() {
        return AI_WasAttacked;
    }

    public void setAI_WasAttacked(boolean AI_WasAttacked) {
        this.AI_WasAttacked = AI_WasAttacked;
    }

    public GameObject getAttackerObject() {
        return attackerObject;
    }

    public void setAttackerObject(GameObject attackerObject) {
        this.attackerObject = attackerObject;
    }

    public Sortie getAttackerSortie() {
        return attackerSortie;
    }

    public void setAttackerSortie(Sortie attackerSortie) {
        this.attackerSortie = attackerSortie;
    }

    public Timestamp getEventGameTime() {
        return eventGameTime;
    }

    public void setEventGameTime(Timestamp eventGameTime) {
        this.eventGameTime = eventGameTime;
    }

    public Timestamp getEventRealTime() {
        return eventRealTime;
    }

    public void setEventRealTime(Timestamp eventRealTime) {
        this.eventRealTime = eventRealTime;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public boolean isObjectAttacked() {
        return objectAttacked;
    }

    public void setObjectAttacked(boolean objectAttacked) {
        this.objectAttacked = objectAttacked;
    }

    public boolean isObjectWasAttacked() {
        return objectWasAttacked;
    }

    public void setObjectWasAttacked(boolean objectWasAttacked) {
        this.objectWasAttacked = objectWasAttacked;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public MissionEventType getType() {
        return type;
    }

    @Override
    public void setType(MissionEventType type) {
        this.type = type;
    }

    public GameObject getVictimObject() {
        return victimObject;
    }

    public void setVictimObject(GameObject victimObject) {
        this.victimObject = victimObject;
    }

    public Sortie getVictimSortie() {
        return victimSortie;
    }

    public void setVictimSortie(Sortie victimSortie) {
        this.victimSortie = victimSortie;
    }
}
