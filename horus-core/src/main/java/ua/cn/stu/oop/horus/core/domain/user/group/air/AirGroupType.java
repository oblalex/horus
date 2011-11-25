package ua.cn.stu.oop.horus.core.domain.user.group.air;

import ua.cn.stu.oop.horus.core.domain.type.EntityType;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.ParentCarrier;
import ua.cn.stu.oop.horus.core.domain.text.LocalizedTitle;

/**
 *
 * @author alex
 */
public class AirGroupType
        extends GenericDomain
        implements EntityType,
        ParentCarrier<AirGroupType> {

    private AirGroupType parent;
    private LocalizedTitle localizedTitle;
    private int maxMembersCount;
    private int minMembersCount;

    public int getMinMembersCount() {
        return minMembersCount;
    }

    public void setMinMembersCount(int minMembersCount) {
        this.minMembersCount = minMembersCount;
    }

    public int getMaxMembersCount() {
        return maxMembersCount;
    }

    public void setMaxMembersCount(int maxMembersCount) {
        this.maxMembersCount = maxMembersCount;
    }

    @Override
    public AirGroupType getParent() {
        return parent;
    }

    @Override
    public void setParent(AirGroupType parent) {
        this.parent = parent;
    }

    public LocalizedTitle getLocalizedTitle() {
        return localizedTitle;
    }

    public void setLocalizedTitle(LocalizedTitle localizedTitle) {
        this.localizedTitle = localizedTitle;
    }
}
