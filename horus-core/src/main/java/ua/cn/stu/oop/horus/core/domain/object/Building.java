package ua.cn.stu.oop.horus.core.domain.object;

import ua.cn.stu.oop.horus.core.domain.type.*;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.territory.TerrainUndulation;

/**
 *
 * @author alex
 */
public class Building
        extends GenericDomain 
        implements TypeCarrier<Building.ShapeType> {

    private GameObject gameObject;
    private ShapeType type;
    private TerrainUndulation maximalAcceptableTerrainUndulation;
    private int angleOfTurnToNorth;
    private float wallWidth;

    public enum ShapeType implements EntityType {

        SQUARE,
        RECTANGLE,
        CIRCLE,
        OTHER
    }

    public int getAngleOfTurnToNorth() {
        return angleOfTurnToNorth;
    }

    public void setAngleOfTurnToNorth(int angleOfTurnToNorth) {
        this.angleOfTurnToNorth = angleOfTurnToNorth;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public TerrainUndulation getMaximalAcceptableTerrainUndulation() {
        return maximalAcceptableTerrainUndulation;
    }

    public void setMaximalAcceptableTerrainUndulation(TerrainUndulation maximalAcceptableTerrainUndulation) {
        this.maximalAcceptableTerrainUndulation = maximalAcceptableTerrainUndulation;
    }

    @Override
    public ShapeType getType() {
        return type;
    }

    @Override
    public void setType(ShapeType type) {
        this.type = type;
    }

    public float getWallWidth() {
        return wallWidth;
    }

    public void setWallWidth(float wallWidth) {
        this.wallWidth = wallWidth;
    }
}
