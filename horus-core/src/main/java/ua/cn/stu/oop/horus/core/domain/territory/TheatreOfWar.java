package ua.cn.stu.oop.horus.core.domain.territory;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class TheatreOfWar extends GenericDomain
        implements TitleLinkCarrier, NodeCarrier {

    private Node node;
    private TitleLink titleLink;
    private Timestamp dateOfOpenning;
    private DBFile image;
    private float imageScaleKmPer100Px;

    public Timestamp getDateOfOpenning() {
        return dateOfOpenning;
    }

    public void setDateOfOpenning(Timestamp dateOfOpenning) {
        this.dateOfOpenning = dateOfOpenning;
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

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }

    public float getImageScaleKmPer100Px() {
        return imageScaleKmPer100Px;
    }

    public void setImageScaleKmPer100Px(float imageScaleKmPer100Px) {
        this.imageScaleKmPer100Px = imageScaleKmPer100Px;
    }
}
