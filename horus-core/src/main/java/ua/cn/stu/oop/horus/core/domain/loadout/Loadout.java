package ua.cn.stu.oop.horus.core.domain.loadout;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.ParentCarrier;

/**
 *
 * @author alex
 */
public class Loadout
        extends GenericDomain
        implements LoadoutEntity, ParentCarrier<Loadout> {
    
    private Loadout parent;
    private TitledLoadoutGameCode titledGameCode;
    
    @Override
    public Loadout getParent() {
        return parent;
    }
    
    @Override
    public void setParent(Loadout parent) {
        this.parent = parent;
    }
    
    public TitledLoadoutGameCode getTitledGameCode() {
        return titledGameCode;
    }
    
    public void setTitledGameCode(TitledLoadoutGameCode titledGameCode) {
        this.titledGameCode = titledGameCode;
    }
}
