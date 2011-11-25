package ua.cn.stu.oop.horus.core.domain.object;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class GameObject
        extends GenericDomain
        implements TitleLinkCarrier {

    private TitleLink titleLink;
    private double capacity;
    private double primaryPrice;
    private double lengthMetres;
    private double widthMetres;
    private double heightMetres;
    private String codeInStaticSectionOfMissionFile;

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getCodeInStaticSectionOfMissionFile() {
        return codeInStaticSectionOfMissionFile;
    }

    public void setCodeInStaticSectionOfMissionFile(String codeInStaticSectionOfMissionFile) {
        this.codeInStaticSectionOfMissionFile = codeInStaticSectionOfMissionFile;
    }

    public double getPrimaryPrice() {
        return primaryPrice;
    }

    public void setPrimaryPrice(double primaryPrice) {
        this.primaryPrice = primaryPrice;
    }

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }

    public double getHeightMetres() {
        return heightMetres;
    }

    public void setHeightMetres(double heightMetres) {
        this.heightMetres = heightMetres;
    }

    public double getLengthMetres() {
        return lengthMetres;
    }

    public void setLengthMetres(double lengthMetres) {
        this.lengthMetres = lengthMetres;
    }

    public double getWidthMetres() {
        return widthMetres;
    }

    public void setWidthMetres(double widthMetres) {
        this.widthMetres = widthMetres;
    }
}
