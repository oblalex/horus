package ua.cn.stu.oop.horus.core.domain.loadout;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.territory.SpacetimeUsageInfo;

/**
 *
 * @author alex
 */
public class TechnologyUnitLoadoutLockInfo
        extends GenericDomain
        implements LoadoutCarrier<TechnologyUnitLoadout> {
    
    private TechnologyUnitLoadout loadout;
    private SpacetimeUsageInfo usageInfo;
    
    @Override
    public TechnologyUnitLoadout getLoadout() {
        return loadout;
    }
    
    @Override
    public void setLoadout(TechnologyUnitLoadout loadout) {
        this.loadout = loadout;
    }
    
    public SpacetimeUsageInfo getUsageInfo() {
        return usageInfo;
    }
    
    public void setUsageInfo(SpacetimeUsageInfo usageInfo) {
        this.usageInfo = usageInfo;
    }
}
