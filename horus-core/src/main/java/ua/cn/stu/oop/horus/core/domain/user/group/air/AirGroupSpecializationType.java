package ua.cn.stu.oop.horus.core.domain.user.group.air;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.Type;
import ua.cn.stu.oop.horus.core.domain.type.TypeCarrier;

/**
 *
 * @author alex
 */
public class AirGroupSpecializationType
        extends GenericDomain
        implements TypeCarrier<Type> {

    private AirGroupSpecialization specialization;
    private Type type;

    public AirGroupSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(AirGroupSpecialization specialization) {
        this.specialization = specialization;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }
}
