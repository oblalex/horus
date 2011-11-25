package ua.cn.stu.oop.horus.core.weather;

import ua.cn.stu.oop.horus.core.domain.type.EntityType;

/**
 *
 * @author alex
 */
public enum WeatherType implements EntityType {

    CLOUDLESS,
    CLEAR,
    HAZE,
    SLIGHT_FOG,
    FOG,
    PRECIPITATION,
    THUNDER
}
