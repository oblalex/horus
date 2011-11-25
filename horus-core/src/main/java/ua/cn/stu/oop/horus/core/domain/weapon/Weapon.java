package ua.cn.stu.oop.horus.core.domain.weapon;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.series.*;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class Weapon
        extends GenericDomain
        implements TitleLinkCarrier, SeriesCarrier {

    private TitleLink titleLink;
    private Series series;
    private float damageRadiusMetres;
    private float powerTNTkg;
    private float massKg;
    private float caliber;
    private float serviceRadiusMetres;
    private float lengthMetres;
    private float diameterMetres;
    private float shootFrequency;
    private float startingVelocityMetresPerSecond;
    private float velocityMetresPerSecond;

    public float getServiceRadiusMetres() {
        return serviceRadiusMetres;
    }

    public void setServiceRadiusMetres(float serviceRadiusMetres) {
        this.serviceRadiusMetres = serviceRadiusMetres;
    }

    public float getDiameterMetres() {
        return diameterMetres;
    }

    public void setDiameterMetres(float diameterMetres) {
        this.diameterMetres = diameterMetres;
    }

    public float getLengthMetres() {
        return lengthMetres;
    }

    public void setLengthMetres(float lengthMetres) {
        this.lengthMetres = lengthMetres;
    }

    public float getCaliber() {
        return caliber;
    }

    public void setCaliber(float caliber) {
        this.caliber = caliber;
    }

    public float getDamageRadiusMetres() {
        return damageRadiusMetres;
    }

    public void setDamageRadiusMetres(float damageRadiusMetres) {
        this.damageRadiusMetres = damageRadiusMetres;
    }

    public float getMassKg() {
        return massKg;
    }

    public void setMassKg(float massKg) {
        this.massKg = massKg;
    }

    public float getPowerTNTkg() {
        return powerTNTkg;
    }

    public void setPowerTNTkg(float powerTNTkg) {
        this.powerTNTkg = powerTNTkg;
    }

    public float getShootFrequency() {
        return shootFrequency;
    }

    public void setShootFrequency(float shootFrequency) {
        this.shootFrequency = shootFrequency;
    }

    public float getVelocityMetresPerSecond() {
        return velocityMetresPerSecond;
    }

    public void setVelocityMetresPerSecond(float velocityMetresPerSecond) {
        this.velocityMetresPerSecond = velocityMetresPerSecond;
    }

    public float getStartingVelocityMetresPerSecond() {
        return startingVelocityMetresPerSecond;
    }

    public void setStartingVelocityMetresPerSecond(float startingVelocityMetresPerSecond) {
        this.startingVelocityMetresPerSecond = startingVelocityMetresPerSecond;
    }

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
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
