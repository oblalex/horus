package ua.cn.stu.oop.horus.core.domain.territory;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class Road
        extends GenericDomain
        implements NodeCarrier, TypeCarrier<Road.RoadType> {

    private Node selfNode;
    private Node placeOneNode;
    private Node placeTwoNode;
    private RoadType type;

    public enum RoadType implements EntityType {

        OFFROAD,
        DIRT_ROAD,
        PAVED_ROAD,
        RAILWAY,
        WATERWAY
    }

    public Node getPlaceOneNode() {
        return placeOneNode;
    }

    public void setPlaceOneNode(Node placeOneNode) {
        this.placeOneNode = placeOneNode;
    }

    public Node getPlaceTwoNode() {
        return placeTwoNode;
    }

    public void setPlaceTwoNode(Node placeTwoNode) {
        this.placeTwoNode = placeTwoNode;
    }

    public Node getSelfNode() {
        return selfNode;
    }

    public void setSelfNode(Node selfNode) {
        this.selfNode = selfNode;
    }

    @Override
    public Node getNode() {
        return selfNode;
    }

    @Override
    public void setNode(Node selfNode) {
        this.selfNode = selfNode;
    }

    @Override
    public RoadType getType() {
        return type;
    }

    @Override
    public void setType(RoadType type) {
        this.type = type;
    }
}
