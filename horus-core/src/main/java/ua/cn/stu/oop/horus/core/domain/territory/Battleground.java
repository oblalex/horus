package ua.cn.stu.oop.horus.core.domain.territory;

import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class Battleground
        extends GenericDomain
        implements TitleLinkCarrier, NodeCarrier {

    private Node node;
    private int lengthMetres;
    private int widthMetres;
    private short timeZoneUTC;
    private String summerVariantLoaderDirectoryTitle;
    private String autumnVariantLoaderDirectoryTitle;
    private String winterVariantLoaderDirectoryTitle;
    private String springVariantLoaderDirectoryTitle;
    private TitleLink titleLink;
    private DBFile image;
    private float imageScaleKmPer100Px;

    public int getLengthMetres() {
        return lengthMetres;
    }

    public void setLengthMetres(int lengthMetres) {
        this.lengthMetres = lengthMetres;
    }

    public int getWidthMetres() {
        return widthMetres;
    }

    public void setWidthMetres(int widthMetres) {
        this.widthMetres = widthMetres;
    }

    public String getAutumnVariantLoaderDirectoryTitle() {
        return autumnVariantLoaderDirectoryTitle;
    }

    public void setAutumnVariantLoaderDirectoryTitle(String autumnVariantLoaderDirectoryTitle) {
        this.autumnVariantLoaderDirectoryTitle = autumnVariantLoaderDirectoryTitle;
    }

    public DBFile getImage() {
        return image;
    }

    public void setImage(DBFile image) {
        this.image = image;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }

    public String getSpringVariantLoaderDirectoryTitle() {
        return springVariantLoaderDirectoryTitle;
    }

    public void setSpringVariantLoaderDirectoryTitle(String springVariantLoaderDirectoryTitle) {
        this.springVariantLoaderDirectoryTitle = springVariantLoaderDirectoryTitle;
    }

    public String getSummerVariantLoaderDirectoryTitle() {
        return summerVariantLoaderDirectoryTitle;
    }

    public void setSummerVariantLoaderDirectoryTitle(String summerVariantLoaderDirectoryTitle) {
        this.summerVariantLoaderDirectoryTitle = summerVariantLoaderDirectoryTitle;
    }

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }

    public String getWinterVariantLoaderDirectoryTitle() {
        return winterVariantLoaderDirectoryTitle;
    }

    public void setWinterVariantLoaderDirectoryTitle(String winterVariantLoaderDirectoryTitle) {
        this.winterVariantLoaderDirectoryTitle = winterVariantLoaderDirectoryTitle;
    }

    public short getTimeZoneUTC() {
        return timeZoneUTC;
    }

    public void setTimeZoneUTC(short timeZoneUTC) {
        this.timeZoneUTC = timeZoneUTC;
    }

    public float getImageScaleKmPer100Px() {
        return imageScaleKmPer100Px;
    }

    public void setImageScaleKmPer100Px(float imageScaleKmPer100Px) {
        this.imageScaleKmPer100Px = imageScaleKmPer100Px;
    }
}
