package ua.cn.stu.oop.horus.core.domain.territory;

import com.vividsolutions.jts.geom.LineString;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class RoadSection
        extends GenericDomain
        implements NodeCarrier {

    private Node node;
    private LineString body;
    private boolean bad;

    public boolean isBad() {
        return bad;
    }

    public void setBad(boolean bad) {
        this.bad = bad;
    }

    public LineString getBody() {
        return body;
    }

    public void setBody(LineString body) {
        this.body = body;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }
}
