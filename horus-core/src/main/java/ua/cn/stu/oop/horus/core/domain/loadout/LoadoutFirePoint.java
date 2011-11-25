package ua.cn.stu.oop.horus.core.domain.loadout;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class LoadoutFirePoint
        extends GenericDomain
        implements LoadoutCarrier<Loadout> {

    private Loadout loadout;
    private SingleFirePoint firePoint;
    private int pointsCount;

    @Override
    public Loadout getLoadout() {
        return loadout;
    }

    @Override
    public void setLoadout(Loadout loadout) {
        this.loadout = loadout;
    }

    public SingleFirePoint getFirePoint() {
        return firePoint;
    }

    public void setFirePoint(SingleFirePoint firePoint) {
        this.firePoint = firePoint;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }
}