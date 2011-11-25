package ua.cn.stu.oop.horus.core.domain.object;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class ObjectType
        extends GenericDomain
        implements EntityType,
        TypeCarrier<Type> {
    
    private Type type;
    private ObjectPrefixInMissionFile prefix;
    private int scoreForFoeKill;
    
    @Override
    public Type getType() {
        return type;
    }
    
    @Override
    public void setType(Type type) {
        this.type = type;
    }
    
    public ObjectPrefixInMissionFile getPrefix() {
        return prefix;
    }
    
    public void setPrefix(ObjectPrefixInMissionFile prefix) {
        this.prefix = prefix;
    }
    
    public int getScoreForFoeKill() {
        return scoreForFoeKill;
    }
    
    public void setScoreForFoeKill(int scoreForFoeKill) {
        this.scoreForFoeKill = scoreForFoeKill;
    }
}
