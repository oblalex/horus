package ua.cn.stu.oop.horus.core.domain.territory;

import com.vividsolutions.jts.geom.Polygon;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class BattlegroundArea
        extends GenericDomain
        implements NodeCarrier, TypeCarrier<BattlegroundArea.AreaType> {

    private Node node;
    private Polygon body;
    private TerrainUndulation terrainUndulation;
    private AreaType type;

    public enum AreaType implements EntityType{

        GROUND_COMMON
    }

    public Polygon getBody() {
        return body;
    }

    public void setBody(Polygon body) {
        this.body = body;
    }

    @Override
    public AreaType getType() {
        return type;
    }

    @Override
    public void setType(AreaType type) {
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

    public TerrainUndulation getTerrainUndulation() {
        return terrainUndulation;
    }

    public void setTerrainUndulation(TerrainUndulation terrainUndulation) {
        this.terrainUndulation = terrainUndulation;
    }
}
