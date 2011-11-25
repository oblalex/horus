package ua.cn.stu.oop.horus.core.service.user.group.air;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.StringTitleCarrierService;
import ua.cn.stu.oop.horus.core.service.type.TypeCarrierService;
import ua.cn.stu.oop.horus.core.service.user.group.GroupLinkCarrierService;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;

/**
 *
 * @author alex
 */
public interface AirGroupService
        extends GenericService<AirGroup>,
        TypeCarrierService<AirGroup>,
        StringTitleCarrierService<AirGroup>,
        GroupLinkCarrierService<AirGroup> {
}
