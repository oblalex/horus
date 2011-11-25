package ua.cn.stu.oop.horus.core.domain.type;

import ua.cn.stu.oop.horus.core.domain.*;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class Type
        extends GenericDomain
        implements TitleLinkCarrier,
        TypeCarrier<BasicType>,
        EntityType,
        ParentCarrier<Type> {
    
    private Type parent;
    private TitleLink titleLink;
    private BasicType type;
    
    @Override
    public Type getParent() {
        return parent;
    }
    
    @Override
    public void setParent(Type parent) {
        this.parent = parent;
    }
    
    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }
    
    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }
    
    @Override
    public BasicType getType() {
        return type;
    }
    
    @Override
    public void setType(BasicType type) {
        this.type = type;
    }
}
