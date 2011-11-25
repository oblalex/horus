package ua.cn.stu.oop.horus.core.domain.territory;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class Airfield
        extends GenericDomain
        implements TitleLinkCarrier,
        NodeCarrier,
        TypeCarrier<Airfield.AirfieldType> {

    private Node node;
    private TitleLink titleLink;
    private AirfieldType type;
    private int rangeMetres;
    private int landingHeading;
    private int aboveMeanSeaLevelMetres;
    private boolean active;
    private int frictionCoefficientX100;
    private int enemyDetectionRangeKm;
    private int enemyDetectionAltitudeMetresMax;
    private int enemyDetectionAltitudeMetresMin;

    public enum AirfieldType implements EntityType {

        CONCRETE,
        GROUND,
        GROUND_AREA,
        GROUND_AREA_UNRAMMED,
        HYDRO
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getLandingHeading() {
        return landingHeading;
    }

    public void setLandingHeading(int landingHeading) {
        this.landingHeading = landingHeading;
    }

    public int getAboveMeanSeaLevelMetres() {
        return aboveMeanSeaLevelMetres;
    }

    public void setAboveMeanSeaLevelMetres(int aboveMeanSeaLevelMetres) {
        this.aboveMeanSeaLevelMetres = aboveMeanSeaLevelMetres;
    }

    public int getRangeMetres() {
        return rangeMetres;
    }

    public void setRangeMetres(int rangeMetres) {
        this.rangeMetres = rangeMetres;
    }

    @Override
    public AirfieldType getType() {
        return type;
    }

    @Override
    public void setType(AirfieldType type) {
        this.type = type;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }

    public int getFrictionCoefficientX100() {
        return frictionCoefficientX100;
    }

    public void setFrictionCoefficientX100(int frictionCoefficientX100) {
        this.frictionCoefficientX100 = frictionCoefficientX100;
    }

    public int getEnemyDetectionAltitudeMetresMax() {
        return enemyDetectionAltitudeMetresMax;
    }

    public void setEnemyDetectionAltitudeMetresMax(int enemyDetectionAltitudeMetresMax) {
        this.enemyDetectionAltitudeMetresMax = enemyDetectionAltitudeMetresMax;
    }

    public int getEnemyDetectionAltitudeMetresMin() {
        return enemyDetectionAltitudeMetresMin;
    }

    public void setEnemyDetectionAltitudeMetresMin(int enemyDetectionAltitudeMetresMin) {
        this.enemyDetectionAltitudeMetresMin = enemyDetectionAltitudeMetresMin;
    }

    public int getEnemyDetectionRangeKm() {
        return enemyDetectionRangeKm;
    }

    public void setEnemyDetectionRangeKm(int enemyDetectionRangeKm) {
        this.enemyDetectionRangeKm = enemyDetectionRangeKm;
    }
}
