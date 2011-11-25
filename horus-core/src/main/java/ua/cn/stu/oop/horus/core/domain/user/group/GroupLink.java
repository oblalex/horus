package ua.cn.stu.oop.horus.core.domain.user.group;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
public class GroupLink
        extends GenericDomain
        implements TypeCarrier<GroupLink.GroupType> {

    private GroupType type;

    public static enum GroupType implements EntityType {

        AIR_GROUP,
        USER_GROUP
    }

    @Override
    public GroupType getType() {
        return type;
    }

    @Override
    public void setType(GroupType type) {
        this.type = type;
    }
}
