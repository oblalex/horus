package ua.cn.stu.oop.horus.core.domain.loadout;

/**
 *
 * @author alex
 */
public interface LoadoutCarrier<L extends LoadoutEntity> {

    public L getLoadout();

    public void setLoadout(L loadout);
}
