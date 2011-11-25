package ua.cn.stu.oop.horus.core.domain.territory;

import com.vividsolutions.jts.geom.Point;
import ua.cn.stu.oop.horus.core.domain.*;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class Node extends GenericDomain implements TypeCarrier<Node.NodeType> {

    private NodeType type;
    private Belligerent belligerent;
    private Node currentOwner;
    private Node defaultOwner;
    private Point point;
    private boolean reconnoitredByFoe;
    private boolean connectedToNeighbourMap;
    private boolean deletable;

    public enum NodeType implements EntityType {

        FRONT,
        HEADQUARTERS,
        MAP,
        TOWN,
        AIRFIELD,
        AREA
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public void setType(NodeType type) {
        this.type = type;
    }

    public Belligerent getBelligerent() {
        return belligerent;
    }

    public void setBelligerent(Belligerent belligerent) {
        this.belligerent = belligerent;
    }

    public boolean isConnectedToNeighbourMap() {
        return connectedToNeighbourMap;
    }

    public void setConnectedToNeighbourMap(boolean connectedToNeighbourMap) {
        this.connectedToNeighbourMap = connectedToNeighbourMap;
    }

    public Node getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(Node currentOwner) {
        this.currentOwner = currentOwner;
    }

    public Node getDefaultOwner() {
        return defaultOwner;
    }

    public void setDefaultOwner(Node defaultOwner) {
        this.defaultOwner = defaultOwner;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean isReconnoitredByFoe() {
        return reconnoitredByFoe;
    }

    public void setReconnoitredByFoe(boolean reconnoitredByFoe) {
        this.reconnoitredByFoe = reconnoitredByFoe;
    }
}
