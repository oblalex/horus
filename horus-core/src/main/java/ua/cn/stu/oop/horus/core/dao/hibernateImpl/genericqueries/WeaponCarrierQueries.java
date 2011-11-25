package ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries;

import ua.cn.stu.oop.horus.core.domain.weapon.WeaponCarrier;

/**
 *
 * @author alex
 */
public class WeaponCarrierQueries {
    private static final String GET_CARRIERS_BY_WEAPON = "FROM %s WHERE weapon = ?";

    public static String getCarriersByWeapon(Class<? extends WeaponCarrier> carrierClass) {
        return String.format(GET_CARRIERS_BY_WEAPON, carrierClass.getName());
    }
}
