package ua.cn.stu.oop.horus.core.domain.loadout;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.object.TechnologyUnit;

/**
 *
 * @author alex
 */
public class TechnologyUnitLoadout
        extends GenericDomain
        implements LoadoutCarrier<Loadout>, LoadoutEntity {

    private TechnologyUnit technologyUnit;
    private Loadout loadout;

    public TechnologyUnit getTechnologyUnit() {
        return technologyUnit;
    }

    public void setTechnologyUnit(TechnologyUnit technologyUnit) {
        this.technologyUnit = technologyUnit;
    }

    @Override
    public Loadout getLoadout() {
        return loadout;
    }

    @Override
    public void setLoadout(Loadout loadout) {
        this.loadout = loadout;
    }
}
