package ua.cn.stu.oop.horus.core.domain.object;

import ua.cn.stu.oop.horus.core.domain.series.*;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class TechnologyUnit
        extends GenericDomain
        implements SeriesCarrier {

    /*
     * value <0 means limitless
     */
    private int quantityProduced;
    private GameObject gameObject;
    private Series series;
    private int maximumSpeedInKMH;
    private int averageSpeedInKMH;
    private int bestDistanceBetweenUnitsInGroupMetres;
    private int maximumRangeInKM;
    private int crew;
    private boolean prototype;
    private boolean canSwim;

    public int getAverageSpeedInKMH() {
        return averageSpeedInKMH;
    }

    public void setAverageSpeedInKMH(int averageSpeedInKMH) {
        this.averageSpeedInKMH = averageSpeedInKMH;
    }

    public int getBestDistanceBetweenUnitsInGroupMetres() {
        return bestDistanceBetweenUnitsInGroupMetres;
    }

    public void setBestDistanceBetweenUnitsInGroupMetres(int bestDistanceBetweenUnitsInGroupMetres) {
        this.bestDistanceBetweenUnitsInGroupMetres = bestDistanceBetweenUnitsInGroupMetres;
    }

    public boolean isCanSwim() {
        return canSwim;
    }

    public void setCanSwim(boolean canSwim) {
        this.canSwim = canSwim;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public int getMaximumRangeInKM() {
        return maximumRangeInKM;
    }

    public void setMaximumRangeInKM(int maximumRangeInKM) {
        this.maximumRangeInKM = maximumRangeInKM;
    }

    public int getMaximumSpeedInKMH() {
        return maximumSpeedInKMH;
    }

    public void setMaximumSpeedInKMH(int maximumSpeedInKMH) {
        this.maximumSpeedInKMH = maximumSpeedInKMH;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public void setPrototype(boolean prototype) {
        this.prototype = prototype;
    }

    public int getQuantityProduced() {
        return quantityProduced;
    }

    public void setQuantityProduced(int quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    public int getCrew() {
        return crew;
    }

    public void setCrew(int crew) {
        this.crew = crew;
    }

    @Override
    public Series getSeries() {
        return series;
    }

    @Override
    public void setSeries(Series series) {
        this.series = series;
    }
}
