package ua.cn.stu.oop.horus.core.domain.user.group.air;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class AirGroupStatus
        extends GenericDomain
        implements TypeCarrier<AirGroupStatus.AirGroupStatusType> {

    private float minimalGroupExperience;
    private short minimalPercentOfMaximalGroupExperience;
    private int requiredGroupCountInAirforce;
    private AirGroupStatusType type;

    public enum AirGroupStatusType implements EntityType {

        POOR,
        NORMAL,
        GOOD,
        VERY_GOOD,
        EXCELLENT
    }

    public float getMinimalGroupExperience() {
        return minimalGroupExperience;
    }

    public void setMinimalGroupExperience(float minimalGroupExperience) {
        this.minimalGroupExperience = minimalGroupExperience;
    }

    public short getMinimalPercentOfMaximalGroupExperience() {
        return minimalPercentOfMaximalGroupExperience;
    }

    public void setMinimalPercentOfMaximalGroupExperience(short minimalPercentOfMaximalGroupExperience) {
        this.minimalPercentOfMaximalGroupExperience = minimalPercentOfMaximalGroupExperience;
    }

    public int getRequiredGroupCountInAirforce() {
        return requiredGroupCountInAirforce;
    }

    public void setRequiredGroupCountInAirforce(int requiredGroupCountInAirforce) {
        this.requiredGroupCountInAirforce = requiredGroupCountInAirforce;
    }

    @Override
    public AirGroupStatusType getType() {
        return type;
    }

    @Override
    public void setType(AirGroupStatusType type) {
        this.type = type;
    }
}
