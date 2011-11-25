package ua.cn.stu.oop.horus.core.domain.weapon;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class WeaponType
        extends GenericDomain
        implements TypeCarrier<Type>, WeaponCarrier {

    private Weapon weapon;
    private Type type;

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
