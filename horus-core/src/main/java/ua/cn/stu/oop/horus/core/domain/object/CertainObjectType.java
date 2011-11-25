package ua.cn.stu.oop.horus.core.domain.object;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.TypeCarrier;

/**
 *
 * @author alex
 */
public class CertainObjectType
        extends GenericDomain
        implements TypeCarrier<ObjectType> {
    
    private GameObject object;
    private ObjectType type;
    
    public GameObject getObject() {
        return object;
    }
    
    public void setObject(GameObject object) {
        this.object = object;
    }
    
    @Override
    public ObjectType getType() {
        return type;
    }
    
    @Override
    public void setType(ObjectType type) {
        this.type = type;
    }
}
