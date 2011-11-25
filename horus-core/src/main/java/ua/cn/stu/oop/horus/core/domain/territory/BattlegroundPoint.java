package ua.cn.stu.oop.horus.core.domain.territory;

import com.vividsolutions.jts.geom.Point;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class BattlegroundPoint
        extends GenericDomain
        implements NodeCarrier, TypeCarrier<BattlegroundPoint.PointType> {

    private Node node;
    private Point point;
    private PointType type;
    private float parameter;

    public enum PointType implements EntityType {

        AIRFIELD_FIREPLACE,
        AIRFIELD_TENTPLACE,
        AIRFIELD_SIRENPLACE,
        AIRFIELD_PROJECTORPLACE
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }

    public float getParameter() {
        return parameter;
    }

    public void setParameter(float parameter) {
        this.parameter = parameter;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public PointType getType() {
        return type;
    }

    @Override
    public void setType(PointType type) {
        this.type = type;
    }
}
