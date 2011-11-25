package ua.cn.stu.oop.horus.core.domain.object;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.territory.SpacetimeUsageInfo;

/**
 *
 * @author alex
 */
public class ObjectUsageInfo extends GenericDomain {

    private GameObject object;
    private SpacetimeUsageInfo usageInfo;

    public GameObject getObject() {
        return object;
    }

    public void setObject(GameObject object) {
        this.object = object;
    }

    public SpacetimeUsageInfo getUsageInfo() {
        return usageInfo;
    }

    public void setUsageInfo(SpacetimeUsageInfo usageInfo) {
        this.usageInfo = usageInfo;
    }
}
