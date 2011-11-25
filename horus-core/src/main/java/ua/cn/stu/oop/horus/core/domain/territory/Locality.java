package ua.cn.stu.oop.horus.core.domain.territory;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class Locality
        extends GenericDomain
        implements TitleLinkCarrier, NodeCarrier, TypeCarrier<Locality.LocalityType> {

    private Node node;
    private TitleLink titleLink;
    private LocalityType type;

    public enum LocalityType implements EntityType {

        VILLAGE,
        TOWN,
        CITY
    }

    @Override
    public LocalityType getType() {
        return type;
    }

    @Override
    public void setType(LocalityType type) {
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
}
