package ua.cn.stu.oop.horus.core.domain.loadout;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class AircraftLoadout
        extends GenericDomain
        implements LoadoutCarrier<TechnologyUnitLoadout> {
    
    private TechnologyUnitLoadout loadout;
    private short index;
    private short maxFuelPercent;
    private short minFuelPercent;
    
    public short getIndex() {
        return index;
    }
    
    public void setIndex(short index) {
        this.index = index;
    }
    
    @Override
    public TechnologyUnitLoadout getLoadout() {
        return loadout;
    }
    
    @Override
    public void setLoadout(TechnologyUnitLoadout loadout) {
        this.loadout = loadout;
    }
    
    public short getMaxFuelPercent() {
        return maxFuelPercent;
    }
    
    public void setMaxFuelPercent(short maxFuelPercent) {
        this.maxFuelPercent = maxFuelPercent;
    }
    
    public short getMinFuelPercent() {
        return minFuelPercent;
    }
    
    public void setMinFuelPercent(short minFuelPercent) {
        this.minFuelPercent = minFuelPercent;
    }
}
