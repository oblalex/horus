package ua.cn.stu.oop.horus.core.domain.loadout;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.weapon.Weapon;
import ua.cn.stu.oop.horus.core.domain.weapon.WeaponCarrier;

/**
 *
 * @author alex
 */
public class SingleFirePoint
        extends GenericDomain
        implements WeaponCarrier {

    private Weapon weapon;
    private int ammo;

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
}
